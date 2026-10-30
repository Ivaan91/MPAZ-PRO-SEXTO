package com.example.mpaz_pro_6.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mpaz_pro_6.model.EstadoInformePedagogico
import com.example.mpaz_pro_6.model.GestionAcademicaRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.model.ResultadoEtapa
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
data class RendimientoInformeUi(
    val nombre: String,
    val porcentaje: Int
)

data class InformePedagogicoUiState(
    val asignaturaId: String = "3",
    // Datos ficticios del caso.
    val estudiante: String = "Sofía Contreras",
    val curso: String = "6.º A",
    val docenteResponsable: String = "Javier Muñoz",
    val fechaGeneracion: String = "",
    val rendimientoGeneral: Int = 0,
    val evaluacionesCompletadas: Int = 0,
    val respuestasCorrectas: Int = 0,
    val respuestasIncorrectas: Int = 0,
    val rendimientos: List<RendimientoInformeUi> = emptyList(),
    val fortalezas: List<String> = emptyList(),
    val porReforzar: List<String> = emptyList(),
    val estado: EstadoInformePedagogico = EstadoInformePedagogico.PENDIENTE,
    val mensaje: String? = null
)

// La asignatura llega como argumento de navegación ("asignaturaId").
class InformePedagogicoViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val asignaturaId: String =
        savedStateHandle.get<String>("asignaturaId") ?: "3"

    // Se fija al abrir el informe.
    private val fechaGeneracion: String =
        SimpleDateFormat("d 'de' MMMM 'de' yyyy, HH:mm", Locale("es", "CL")).format(Date())

    private val _uiState = MutableStateFlow(crearEstado())
    val uiState: StateFlow<InformePedagogicoUiState> = _uiState.asStateFlow()

    fun actualizar() {
        _uiState.value = crearEstado(mensaje = _uiState.value.mensaje)
    }

    fun validarInforme() {
        val progreso = ProgresoRepository.de(asignaturaId)
        val tieneResultados =
            progreso.actividad != null ||
                    progreso.desafio != null ||
                    progreso.intentosPrueba.isNotEmpty()

        if (!tieneResultados) {
            _uiState.value = _uiState.value.copy(
                mensaje = "Todavía no existen resultados suficientes para validar el informe."
            )
            return
        }

        GestionAcademicaRepository.validarInforme(asignaturaId)
        _uiState.value = crearEstado(mensaje = "Informe validado correctamente.")
    }

    private fun crearEstado(mensaje: String? = null): InformePedagogicoUiState {
        val progreso = ProgresoRepository.de(asignaturaId)
        val actividad = progreso.actividad
        val desafio = progreso.desafio
        val prueba = progreso.intentosPrueba.lastOrNull()

        val resultados = listOfNotNull(actividad, desafio, prueba)

        val promedio =
            if (resultados.isEmpty()) 0
            else resultados.map { it.porcentaje }.average().toInt()

        val correctas = resultados.sumOf { it.correctas }
        val total = resultados.sumOf { it.total }

        val rendimientos = listOf(
            etapa("Actividad de práctica", actividad),
            etapa("Desafío aplicado", desafio),
            etapa("Prueba final", prueba)
        )

        val rendimientoTemas = calcularRendimientoPorTema(resultados)

        val fortalezas = rendimientoTemas
            .filter { it.value >= UMBRAL_LOGRADO }
            .toList()
            .sortedByDescending { it.second }
            .take(3)
            .map { "${it.first} · ${it.second}%" }

        val porReforzar = rendimientoTemas
            .filter { it.value < UMBRAL_LOGRADO }
            .toList()
            .sortedBy { it.second }
            .take(3)
            .map { "${it.first} · ${it.second}%" }

        return InformePedagogicoUiState(
            asignaturaId = asignaturaId,
            fechaGeneracion = fechaGeneracion,
            rendimientoGeneral = promedio,
            evaluacionesCompletadas = resultados.size,
            respuestasCorrectas = correctas,
            respuestasIncorrectas = total - correctas,
            rendimientos = rendimientos,
            fortalezas = fortalezas,
            porReforzar = porReforzar,
            estado = GestionAcademicaRepository.estadoInforme(asignaturaId),
            mensaje = mensaje
        )
    }

    private fun etapa(nombre: String, resultado: ResultadoEtapa?): RendimientoInformeUi =
        RendimientoInformeUi(
            nombre = if (resultado == null) "$nombre · sin realizar"
            else "$nombre · ${resultado.correctas} de ${resultado.total}",
            porcentaje = resultado?.porcentaje ?: 0
        )

    private fun calcularRendimientoPorTema(
        resultados: List<ResultadoEtapa>
    ): Map<String, Int> {
        val datos = mutableMapOf<String, Pair<Int, Int>>()

        resultados.forEach { resultado ->
            resultado.preguntas.forEachIndexed { indice, pregunta ->
                val anterior = datos[pregunta.tema] ?: Pair(0, 0)
                val correcta =
                    if (resultado.elegidas.getOrNull(indice) == pregunta.correcta) 1 else 0
                datos[pregunta.tema] = Pair(anterior.first + correcta, anterior.second + 1)
            }
        }

        return datos.mapValues { (_, valores) ->
            val correctas = valores.first
            val total = valores.second
            if (total == 0) 0 else correctas * 100 / total
        }
    }

    private companion object {
        const val UMBRAL_LOGRADO = 70
    }
}