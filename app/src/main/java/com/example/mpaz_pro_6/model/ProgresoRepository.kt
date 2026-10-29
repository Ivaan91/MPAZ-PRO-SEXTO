package com.example.mpaz_pro_6.model

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateMapOf
import org.json.JSONArray
import org.json.JSONObject


/**
 * Resultado de una etapa:
 * contiene las respuestas elegidas por
 * el estudiante y las preguntas asociadas.
 */
data class ResultadoEtapa(
    val elegidas: List<Int>,
    val preguntas: List<PreguntaQuiz>
) {

    val total: Int
        get() =
            preguntas.size


    val correctas: Int
        get() =
            preguntas.indices.count {

                elegidas.getOrNull(it) ==
                        preguntas[it].correcta
            }


    val porcentaje: Int
        get() =

            if (total == 0) {

                0

            } else {

                correctas * 100 / total
            }
}


data class ProgresoUnidad(

    val contenidosLeidos:
    Set<Int> =
        emptySet(),

    val actividad:
    ResultadoEtapa? =
        null,

    val desafio:
    ResultadoEtapa? =
        null,

    val intentosPrueba:
    List<ResultadoEtapa> =
        emptyList()
)


object ProgresoRepository {


    private const val
            PREFERENCIAS =
        "mpaz_progreso"


    private const val
            CLAVE_DATOS =
        "datos_progreso"


    private lateinit var
            preferencias:
            SharedPreferences


    private var inicializado =
        false


    /*
     * mutableStateMapOf permite que
     * las pantallas Compose reaccionen
     * cuando cambia el progreso.
     */
    private val estado =
        mutableStateMapOf<
                String,
                ProgresoUnidad
                >()


    /*
     * Intentos máximos configurados
     * por el docente.
     */
    private val limites =
        mutableStateMapOf(
            "1" to 3,
            "2" to 3,
            "3" to 3,
            "4" to 3
        )


    // -------------------------
    // INICIALIZACIÓN
    // -------------------------

    fun inicializar(
        context: Context
    ) {

        if (inicializado) {
            return
        }


        preferencias =
            context
                .applicationContext
                .getSharedPreferences(
                    PREFERENCIAS,
                    Context.MODE_PRIVATE
                )


        inicializado =
            true


        cargarDatos()
    }


    // -------------------------
    // PROGRESO
    // -------------------------

    fun de(
        asignaturaId: String
    ): ProgresoUnidad {

        return estado[
            asignaturaId
        ] ?: ProgresoUnidad()
    }


    // -------------------------
    // INTENTOS
    // -------------------------

    fun limiteIntentos(
        asignaturaId: String
    ): Int {

        return limites[
            asignaturaId
        ] ?: 3
    }


    fun configurarLimiteIntentos(
        asignaturaId: String,
        limite: Int
    ) {

        limites[
            asignaturaId
        ] =
            limite.coerceIn(
                1,
                10
            )


        guardarDatos()
    }


    fun intentosUsados(
        asignaturaId: String
    ): Int {

        return de(
            asignaturaId
        ).intentosPrueba.size
    }


    fun intentosRestantes(
        asignaturaId: String
    ): Int {

        return (
                limiteIntentos(
                    asignaturaId
                )
                        -
                        intentosUsados(
                            asignaturaId
                        )
                )
            .coerceAtLeast(0)
    }


    // -------------------------
    // CONTENIDOS
    // -------------------------

    fun marcarContenido(
        asignaturaId: String,
        indice: Int
    ) {

        val progreso =
            de(
                asignaturaId
            )


        estado[
            asignaturaId
        ] =
            progreso.copy(

                contenidosLeidos =
                    progreso
                        .contenidosLeidos +
                            indice
            )


        guardarDatos()
    }


    // -------------------------
    // RESULTADOS
    // -------------------------

    fun guardarResultado(
        asignaturaId: String,
        etapa: Etapa,
        resultado: ResultadoEtapa
    ) {

        val progreso =
            de(
                asignaturaId
            )


        estado[
            asignaturaId
        ] =

            when (etapa) {

                Etapa.ACTIVIDAD ->

                    progreso.copy(
                        actividad =
                            resultado
                    )


                Etapa.DESAFIO ->

                    progreso.copy(
                        desafio =
                            resultado
                    )


                Etapa.PRUEBA -> {

                    if (
                        intentosRestantes(
                            asignaturaId
                        ) > 0
                    ) {

                        progreso.copy(

                            intentosPrueba =
                                progreso
                                    .intentosPrueba +
                                        resultado
                        )

                    } else {

                        progreso
                    }
                }
            }


        guardarDatos()
    }


    // -------------------------
    // CONSULTAS
    // -------------------------

    fun contenidosCompletos(
        asignaturaId: String
    ): Boolean {

        return de(
            asignaturaId
        ).contenidosLeidos.size >=

                MockRepository
                    .unidadDe(
                        asignaturaId
                    )
                    .contenidos
                    .size
    }


    fun desbloqueada(
        asignaturaId: String,
        etapa: Etapa
    ): Boolean {

        val progreso =
            de(
                asignaturaId
            )


        return when (etapa) {

            Etapa.ACTIVIDAD ->

                contenidosCompletos(
                    asignaturaId
                )


            Etapa.DESAFIO ->

                progreso.actividad !=
                        null


            Etapa.PRUEBA ->

                progreso.desafio !=
                        null
        }
    }


    fun avance(
        asignaturaId: String
    ): Float {

        val progreso =
            de(
                asignaturaId
            )


        val pasos =
            MockRepository
                .unidadDe(
                    asignaturaId
                )
                .contenidos
                .size + 3


        val hechos =

            progreso
                .contenidosLeidos
                .size +

                    (
                            if (
                                progreso.actividad !=
                                null
                            ) {
                                1
                            } else {
                                0
                            }
                            ) +

                    (
                            if (
                                progreso.desafio !=
                                null
                            ) {
                                1
                            } else {
                                0
                            }
                            ) +

                    (
                            if (
                                progreso
                                    .intentosPrueba
                                    .isNotEmpty()
                            ) {
                                1
                            } else {
                                0
                            }
                            )


        return (
                hechos.toFloat() /
                        pasos
                )
            .coerceIn(
                0f,
                1f
            )
    }


    fun avanceGeneral():
            Float {

        return MockRepository
            .asignaturas
            .map {

                avance(
                    it.id
                )
            }
            .average()
            .toFloat()
    }


    // -------------------------
    // REINICIAR PROGRESO
    // -------------------------

    fun reiniciar() {

        estado.clear()

        guardarDatos()
    }


    // -------------------------
    // GUARDAR EN EL TELÉFONO
    // -------------------------

    private fun guardarDatos() {

        if (!inicializado) {
            return
        }


        try {

            val raiz =
                JSONObject()


            // -------------------------
            // LÍMITES
            // -------------------------

            val limitesJson =
                JSONObject()


            limites.forEach {
                    (asignaturaId, limite) ->

                limitesJson.put(
                    asignaturaId,
                    limite
                )
            }


            raiz.put(
                "limites",
                limitesJson
            )


            // -------------------------
            // PROGRESO
            // -------------------------

            val progresoJson =
                JSONObject()


            MockRepository
                .asignaturas
                .forEach {
                        asignatura ->


                    val progreso =
                        de(
                            asignatura.id
                        )


                    val unidadJson =
                        JSONObject()


                    // CONTENIDOS LEÍDOS

                    unidadJson.put(

                        "contenidos",

                        JSONArray(
                            progreso
                                .contenidosLeidos
                                .sorted()
                        )
                    )


                    // ACTIVIDAD

                    progreso.actividad
                        ?.let {
                                resultado ->

                            unidadJson.put(

                                "actividad",

                                JSONArray(
                                    resultado.elegidas
                                )
                            )
                        }


                    // DESAFÍO

                    progreso.desafio
                        ?.let {
                                resultado ->

                            unidadJson.put(

                                "desafio",

                                JSONArray(
                                    resultado.elegidas
                                )
                            )
                        }


                    // PRUEBAS

                    val pruebasJson =
                        JSONArray()


                    progreso
                        .intentosPrueba
                        .forEach {
                                resultado ->

                            pruebasJson.put(

                                JSONArray(
                                    resultado.elegidas
                                )
                            )
                        }


                    unidadJson.put(
                        "pruebas",
                        pruebasJson
                    )


                    progresoJson.put(
                        asignatura.id,
                        unidadJson
                    )
                }


            raiz.put(
                "progreso",
                progresoJson
            )


            preferencias
                .edit()
                .putString(
                    CLAVE_DATOS,
                    raiz.toString()
                )
                .apply()

        } catch (
            excepcion: Exception
        ) {

            /*
             * Si ocurre un problema al
             * serializar, evitamos que
             * la aplicación se cierre.
             */
            excepcion
                .printStackTrace()
        }
    }


    // -------------------------
    // CARGAR AL ABRIR LA APP
    // -------------------------

    private fun cargarDatos() {

        val datosGuardados =
            preferencias.getString(
                CLAVE_DATOS,
                null
            ) ?: return


        try {

            val raiz =
                JSONObject(
                    datosGuardados
                )


            // -------------------------
            // LÍMITES
            // -------------------------

            raiz
                .optJSONObject(
                    "limites"
                )
                ?.let {
                        limitesJson ->


                    MockRepository
                        .asignaturas
                        .forEach {
                                asignatura ->


                            limites[
                                asignatura.id
                            ] =
                                limitesJson
                                    .optInt(
                                        asignatura.id,
                                        3
                                    )
                                    .coerceIn(
                                        1,
                                        10
                                    )
                        }
                }


            // -------------------------
            // PROGRESO
            // -------------------------

            val progresoJson =
                raiz.optJSONObject(
                    "progreso"
                ) ?: return


            MockRepository
                .asignaturas
                .forEach {
                        asignatura ->


                    val asignaturaId =
                        asignatura.id


                    val unidadJson =
                        progresoJson
                            .optJSONObject(
                                asignaturaId
                            )
                            ?: return@forEach


                    // CONTENIDOS

                    val contenidosLeidos =
                        jsonArrayALista(
                            unidadJson
                                .optJSONArray(
                                    "contenidos"
                                )
                        )
                            .toSet()


                    // ACTIVIDAD

                    val actividad =

                        if (
                            unidadJson.has(
                                "actividad"
                            )
                        ) {

                            ResultadoEtapa(

                                elegidas =
                                    jsonArrayALista(
                                        unidadJson
                                            .optJSONArray(
                                                "actividad"
                                            )
                                    ),

                                preguntas =
                                    MockRepository
                                        .preguntasDe(
                                            asignaturaId,
                                            Etapa.ACTIVIDAD
                                        )
                            )

                        } else {

                            null
                        }


                    // DESAFÍO

                    val desafio =

                        if (
                            unidadJson.has(
                                "desafio"
                            )
                        ) {

                            ResultadoEtapa(

                                elegidas =
                                    jsonArrayALista(
                                        unidadJson
                                            .optJSONArray(
                                                "desafio"
                                            )
                                    ),

                                preguntas =
                                    MockRepository
                                        .preguntasDe(
                                            asignaturaId,
                                            Etapa.DESAFIO
                                        )
                            )

                        } else {

                            null
                        }


                    // PRUEBAS

                    val intentos =
                        mutableListOf<
                                ResultadoEtapa
                                >()


                    val pruebasJson =
                        unidadJson
                            .optJSONArray(
                                "pruebas"
                            )


                    if (
                        pruebasJson != null
                    ) {

                        for (
                        indice in
                        0 until
                                pruebasJson.length()
                        ) {

                            val respuestas =
                                pruebasJson
                                    .optJSONArray(
                                        indice
                                    )
                                    ?: continue


                            intentos.add(

                                ResultadoEtapa(

                                    elegidas =
                                        jsonArrayALista(
                                            respuestas
                                        ),

                                    preguntas =
                                        MockRepository
                                            .preguntasDe(
                                                asignaturaId,
                                                Etapa.PRUEBA
                                            )
                                )
                            )
                        }
                    }


                    estado[
                        asignaturaId
                    ] =
                        ProgresoUnidad(

                            contenidosLeidos =
                                contenidosLeidos,

                            actividad =
                                actividad,

                            desafio =
                                desafio,

                            intentosPrueba =
                                intentos
                        )
                }

        } catch (
            excepcion: Exception
        ) {

            /*
             * Datos corruptos o
             * incompatibles:
             *
             * la app continúa usando
             * los valores por defecto.
             */
            excepcion
                .printStackTrace()
        }
    }


    // -------------------------
    // AYUDA JSON
    // -------------------------

    private fun jsonArrayALista(
        array: JSONArray?
    ): List<Int> {

        if (array == null) {
            return emptyList()
        }


        val lista =
            mutableListOf<Int>()


        for (
        indice in
        0 until array.length()
        ) {

            lista.add(
                array.optInt(
                    indice
                )
            )
        }


        return lista
    }
}