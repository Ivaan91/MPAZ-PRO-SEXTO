package com.example.mpaz_pro_6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mpaz_pro_6.model.EstadoInformePedagogico
import com.example.mpaz_pro_6.model.GestionAcademicaRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.model.ResultadoEtapa
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class RendimientoInformeUi(
    val nombre: String,
    val porcentaje: Int
)


data class InformePedagogicoUiState(

    val asignaturaId: String = "3",

    val rendimientoGeneral: Int = 0,

    val evaluacionesCompletadas: Int = 0,

    val rendimientos:
    List<RendimientoInformeUi> =
        emptyList(),

    val fortalezas:
    List<String> =
        emptyList(),

    val porReforzar:
    List<String> =
        emptyList(),

    val estado:
    EstadoInformePedagogico =
        EstadoInformePedagogico.PENDIENTE,

    val mensaje:
    String? = null
)


class InformePedagogicoViewModel :
    ViewModel() {

    /*
     * El mockup del informe pedagógico
     * corresponde actualmente a Ciencias.
     */
    private val asignaturaId =
        "3"


    private val _uiState =
        MutableStateFlow(
            crearEstado()
        )


    val uiState:
            StateFlow<InformePedagogicoUiState> =
        _uiState.asStateFlow()


    fun actualizar() {

        _uiState.value =
            crearEstado(
                mensaje =
                    _uiState.value.mensaje
            )
    }


    fun validarInforme() {

        val progreso =
            ProgresoRepository
                .de(asignaturaId)


        val tieneResultados =

            progreso.actividad != null ||
                    progreso.desafio != null ||
                    progreso
                        .intentosPrueba
                        .isNotEmpty()


        if (!tieneResultados) {

            _uiState.value =
                _uiState.value.copy(

                    mensaje =
                        "Todavía no existen resultados suficientes para validar el informe."
                )

            return
        }


        GestionAcademicaRepository
            .validarInforme(
                asignaturaId
            )


        _uiState.value =
            crearEstado(
                mensaje =
                    "Informe validado correctamente."
            )
    }


    private fun crearEstado(
        mensaje: String? = null
    ): InformePedagogicoUiState {

        val progreso =
            ProgresoRepository
                .de(asignaturaId)


        val actividad =
            progreso.actividad


        val desafio =
            progreso.desafio


        /*
         * Para la prueba final usamos el
         * intento más reciente.
         */
        val prueba =
            progreso
                .intentosPrueba
                .lastOrNull()


        val resultados =
            listOfNotNull(
                actividad,
                desafio,
                prueba
            )


        val promedio =

            if (
                resultados.isEmpty()
            ) {

                0

            } else {

                resultados
                    .map {
                        it.porcentaje
                    }
                    .average()
                    .toInt()
            }


        val rendimientos =
            listOf(

                RendimientoInformeUi(
                    nombre =
                        "Actividad de práctica",
                    porcentaje =
                        actividad
                            ?.porcentaje
                            ?: 0
                ),

                RendimientoInformeUi(
                    nombre =
                        "Desafío aplicado",
                    porcentaje =
                        desafio
                            ?.porcentaje
                            ?: 0
                ),

                RendimientoInformeUi(
                    nombre =
                        "Prueba final",
                    porcentaje =
                        prueba
                            ?.porcentaje
                            ?: 0
                )
            )


        val rendimientoTemas =
            calcularRendimientoPorTema(
                resultados
            )


        val fortalezas =
            rendimientoTemas
                .filter {
                    it.value >= 70
                }
                .toList()
                .sortedByDescending {
                    it.second
                }
                .take(3)
                .map {
                    "${it.first} · ${it.second}%"
                }


        val porReforzar =
            rendimientoTemas
                .filter {
                    it.value < 70
                }
                .toList()
                .sortedBy {
                    it.second
                }
                .take(3)
                .map {
                    "${it.first} · ${it.second}%"
                }


        return InformePedagogicoUiState(

            asignaturaId =
                asignaturaId,

            rendimientoGeneral =
                promedio,

            evaluacionesCompletadas =
                resultados.size,

            rendimientos =
                rendimientos,

            fortalezas =
                fortalezas,

            porReforzar =
                porReforzar,

            estado =
                GestionAcademicaRepository
                    .estadoInforme(
                        asignaturaId
                    ),

            mensaje =
                mensaje
        )
    }


    private fun calcularRendimientoPorTema(
        resultados:
        List<ResultadoEtapa>
    ): Map<String, Int> {

        /*
         * tema ->
         * Pair(correctas, total)
         */
        val datos =
            mutableMapOf<
                    String,
                    Pair<Int, Int>
                    >()


        resultados.forEach {
                resultado ->

            resultado
                .preguntas
                .forEachIndexed {
                        indice,
                        pregunta ->

                    val anterior =
                        datos[
                            pregunta.tema
                        ] ?: Pair(0, 0)


                    val correcta =

                        if (
                            resultado
                                .elegidas
                                .getOrNull(indice)
                            ==
                            pregunta.correcta
                        ) {

                            1

                        } else {

                            0
                        }


                    datos[
                        pregunta.tema
                    ] =
                        Pair(
                            anterior.first +
                                    correcta,

                            anterior.second +
                                    1
                        )
                }
        }


        return datos
            .mapValues {
                    (_, valores) ->

                val correctas =
                    valores.first

                val total =
                    valores.second


                if (total == 0) {

                    0

                } else {

                    correctas *
                            100 /
                            total
                }
            }
    }
}