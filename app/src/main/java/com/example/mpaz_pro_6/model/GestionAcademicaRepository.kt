package com.example.mpaz_pro_6.model

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateMapOf
import org.json.JSONObject


enum class EstadoRevisionUnidad(
    val texto: String
) {
    EN_REVISION("En revisión"),
    AUTORIZADA("Autorizada"),
    CAMBIOS_SOLICITADOS("Cambios solicitados")
}


data class RevisionUnidad(
    val asignaturaId: String,
    val estado: EstadoRevisionUnidad =
        EstadoRevisionUnidad.EN_REVISION,
    val observacion: String = ""
)


data class ConfiguracionAcademicaUnidad(
    val asignaturaId: String,
    val oaSeleccionado: String,
    val contenidoAsignado: Boolean = true,
    val desafioAsignado: Boolean = true
)


enum class EstadoInformePedagogico(
    val texto: String
) {
    PENDIENTE("Pendiente de validar"),
    VALIDADO("Validado")
}


object GestionAcademicaRepository {

    private const val PREFERENCIAS =
        "mpaz_gestion_academica"

    private const val CLAVE_DATOS =
        "datos_gestion"


    private lateinit var preferencias:
            SharedPreferences

    private var inicializado =
        false


    private val revisiones =
        mutableStateMapOf<
                String,
                RevisionUnidad
                >()


    private val configuraciones =
        mutableStateMapOf<
                String,
                ConfiguracionAcademicaUnidad
                >()


    private val estadosInforme =
        mutableStateMapOf<
                String,
                EstadoInformePedagogico
                >()


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
            context.applicationContext
                .getSharedPreferences(
                    PREFERENCIAS,
                    Context.MODE_PRIVATE
                )


        inicializado =
            true


        cargarDatos()
    }


    // -------------------------
    // CONFIGURACIÓN DOCENTE
    // -------------------------

    fun configuracionDe(
        asignaturaId: String
    ): ConfiguracionAcademicaUnidad {

        return configuraciones[
            asignaturaId
        ] ?: configuracionPredeterminada(
            asignaturaId
        )
    }


    private fun configuracionPredeterminada(
        asignaturaId: String
    ): ConfiguracionAcademicaUnidad {

        val unidad =
            MockRepository.unidadDe(
                asignaturaId
            )


        /*
         * Algunas unidades, como Matemática,
         * poseen más de un OA escrito como:
         *
         * MA06 OA 05 · MA06 OA 06
         *
         * Por defecto usamos el primero.
         */
        val oaPrincipal =
            unidad.oa
                .split(" · ")
                .firstOrNull()
                ?.trim()
                .orEmpty()
                .ifBlank {
                    unidad.oa
                }


        return ConfiguracionAcademicaUnidad(
            asignaturaId =
                asignaturaId,

            oaSeleccionado =
                oaPrincipal,

            contenidoAsignado =
                true,

            desafioAsignado =
                true
        )
    }


    fun guardarConfiguracionUnidad(
        asignaturaId: String,
        oaSeleccionado: String,
        contenidoAsignado: Boolean,
        desafioAsignado: Boolean
    ) {

        configuraciones[
            asignaturaId
        ] =
            ConfiguracionAcademicaUnidad(

                asignaturaId =
                    asignaturaId,

                oaSeleccionado =
                    oaSeleccionado.trim(),

                contenidoAsignado =
                    contenidoAsignado,

                desafioAsignado =
                    desafioAsignado
            )


        /*
         * Si el docente modifica una unidad,
         * una autorización anterior deja de ser
         * válida y debe volver a UTP.
         */
        revisiones[
            asignaturaId
        ] =
            RevisionUnidad(

                asignaturaId =
                    asignaturaId,

                estado =
                    EstadoRevisionUnidad
                        .EN_REVISION,

                observacion =
                    ""
            )


        guardarDatos()
    }


    // -------------------------
    // REVISIÓN UTP
    // -------------------------

    fun revisionDe(
        asignaturaId: String
    ): RevisionUnidad {

        return revisiones[
            asignaturaId
        ] ?: RevisionUnidad(
            asignaturaId =
                asignaturaId
        )
    }


    fun autorizarUnidad(
        asignaturaId: String
    ) {

        revisiones[
            asignaturaId
        ] =
            RevisionUnidad(

                asignaturaId =
                    asignaturaId,

                estado =
                    EstadoRevisionUnidad
                        .AUTORIZADA,

                observacion =
                    ""
            )


        guardarDatos()
    }


    fun solicitarCambios(
        asignaturaId: String,
        observacion: String
    ) {

        revisiones[
            asignaturaId
        ] =
            RevisionUnidad(

                asignaturaId =
                    asignaturaId,

                estado =
                    EstadoRevisionUnidad
                        .CAMBIOS_SOLICITADOS,

                observacion =
                    observacion.trim()
            )


        guardarDatos()
    }


    // -------------------------
    // DISPONIBILIDAD ESTUDIANTE
    // -------------------------

    fun unidadDisponibleEstudiante(
        asignaturaId: String
    ): Boolean {

        val configuracion =
            configuracionDe(
                asignaturaId
            )


        val revision =
            revisionDe(
                asignaturaId
            )


        return configuracion
            .oaSeleccionado
            .isNotBlank() &&

                configuracion
                    .contenidoAsignado &&

                configuracion
                    .desafioAsignado &&

                revision.estado ==
                EstadoRevisionUnidad
                    .AUTORIZADA
    }


    fun motivoBloqueoEstudiante(
        asignaturaId: String
    ): String? {

        val configuracion =
            configuracionDe(
                asignaturaId
            )


        val revision =
            revisionDe(
                asignaturaId
            )


        if (
            configuracion
                .oaSeleccionado
                .isBlank() ||
            !configuracion
                .contenidoAsignado ||
            !configuracion
                .desafioAsignado
        ) {

            return "Pendiente de configuración docente"
        }


        return when (
            revision.estado
        ) {

            EstadoRevisionUnidad.AUTORIZADA ->
                null

            EstadoRevisionUnidad.EN_REVISION ->
                "Pendiente de autorización UTP"

            EstadoRevisionUnidad.CAMBIOS_SOLICITADOS ->
                "UTP solicitó cambios"
        }
    }


    // -------------------------
    // INFORME PEDAGÓGICO
    // -------------------------

    fun estadoInforme(
        asignaturaId: String
    ): EstadoInformePedagogico {

        return estadosInforme[
            asignaturaId
        ] ?: EstadoInformePedagogico
            .PENDIENTE
    }


    fun validarInforme(
        asignaturaId: String
    ) {

        estadosInforme[
            asignaturaId
        ] =
            EstadoInformePedagogico
                .VALIDADO


        guardarDatos()
    }


    // -------------------------
    // GUARDAR
    // -------------------------

    private fun guardarDatos() {

        if (!inicializado) {
            return
        }


        try {

            val raiz =
                JSONObject()


            // CONFIGURACIONES DOCENTE

            val configuracionesJson =
                JSONObject()


            MockRepository
                .asignaturas
                .forEach { asignatura ->

                    val configuracion =
                        configuracionDe(
                            asignatura.id
                        )


                    val json =
                        JSONObject()


                    json.put(
                        "oa",
                        configuracion
                            .oaSeleccionado
                    )


                    json.put(
                        "contenidoAsignado",
                        configuracion
                            .contenidoAsignado
                    )


                    json.put(
                        "desafioAsignado",
                        configuracion
                            .desafioAsignado
                    )


                    configuracionesJson.put(
                        asignatura.id,
                        json
                    )
                }


            raiz.put(
                "configuraciones",
                configuracionesJson
            )


            // REVISIONES UTP

            val revisionesJson =
                JSONObject()


            MockRepository
                .asignaturas
                .forEach { asignatura ->

                    val revision =
                        revisionDe(
                            asignatura.id
                        )


                    val json =
                        JSONObject()


                    json.put(
                        "estado",
                        revision.estado.name
                    )


                    json.put(
                        "observacion",
                        revision.observacion
                    )


                    revisionesJson.put(
                        asignatura.id,
                        json
                    )
                }


            raiz.put(
                "revisiones",
                revisionesJson
            )


            // INFORMES

            val informesJson =
                JSONObject()


            MockRepository
                .asignaturas
                .forEach { asignatura ->

                    informesJson.put(

                        asignatura.id,

                        estadoInforme(
                            asignatura.id
                        ).name
                    )
                }


            raiz.put(
                "informes",
                informesJson
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

            excepcion
                .printStackTrace()
        }
    }


    // -------------------------
    // CARGAR
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


            // CONFIGURACIÓN DOCENTE

            val configuracionesJson =
                raiz.optJSONObject(
                    "configuraciones"
                )


            MockRepository
                .asignaturas
                .forEach { asignatura ->

                    val json =
                        configuracionesJson
                            ?.optJSONObject(
                                asignatura.id
                            )


                    if (json != null) {

                        val predeterminada =
                            configuracionPredeterminada(
                                asignatura.id
                            )


                        configuraciones[
                            asignatura.id
                        ] =
                            ConfiguracionAcademicaUnidad(

                                asignaturaId =
                                    asignatura.id,

                                oaSeleccionado =
                                    json.optString(
                                        "oa",
                                        predeterminada
                                            .oaSeleccionado
                                    ),

                                contenidoAsignado =
                                    json.optBoolean(
                                        "contenidoAsignado",
                                        true
                                    ),

                                desafioAsignado =
                                    json.optBoolean(
                                        "desafioAsignado",
                                        true
                                    )
                            )
                    }
                }


            // REVISIONES

            val revisionesJson =
                raiz.optJSONObject(
                    "revisiones"
                )


            MockRepository
                .asignaturas
                .forEach { asignatura ->

                    val json =
                        revisionesJson
                            ?.optJSONObject(
                                asignatura.id
                            )


                    if (json != null) {

                        val estado =
                            runCatching {

                                EstadoRevisionUnidad
                                    .valueOf(
                                        json.optString(
                                            "estado"
                                        )
                                    )

                            }.getOrDefault(
                                EstadoRevisionUnidad
                                    .EN_REVISION
                            )


                        revisiones[
                            asignatura.id
                        ] =
                            RevisionUnidad(

                                asignaturaId =
                                    asignatura.id,

                                estado =
                                    estado,

                                observacion =
                                    json.optString(
                                        "observacion",
                                        ""
                                    )
                            )
                    }
                }


            // INFORMES

            val informesJson =
                raiz.optJSONObject(
                    "informes"
                )


            MockRepository
                .asignaturas
                .forEach { asignatura ->

                    val estadoTexto =
                        informesJson
                            ?.optString(
                                asignatura.id,
                                EstadoInformePedagogico
                                    .PENDIENTE
                                    .name
                            )
                            ?: EstadoInformePedagogico
                                .PENDIENTE
                                .name


                    estadosInforme[
                        asignatura.id
                    ] =
                        runCatching {

                            EstadoInformePedagogico
                                .valueOf(
                                    estadoTexto
                                )

                        }.getOrDefault(
                            EstadoInformePedagogico
                                .PENDIENTE
                        )
                }

        } catch (
            excepcion: Exception
        ) {

            excepcion
                .printStackTrace()
        }
    }
}