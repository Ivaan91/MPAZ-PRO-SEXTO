package com.example.mpaz_pro_6.model

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject


enum class EstadoRevisionUnidad(
    val texto: String
) {

    EN_REVISION(
        "En revisión"
    ),

    AUTORIZADA(
        "Autorizada"
    ),

    CAMBIOS_SOLICITADOS(
        "Cambios solicitados"
    )
}


data class RevisionUnidad(

    val asignaturaId: String,

    val estado:
    EstadoRevisionUnidad =
        EstadoRevisionUnidad.EN_REVISION,

    val observacion:
    String = ""
)


enum class EstadoInformePedagogico(
    val texto: String
) {

    PENDIENTE(
        "Pendiente de validar"
    ),

    VALIDADO(
        "Validado"
    )
}


object GestionAcademicaRepository {


    private const val
            PREFERENCIAS =
        "mpaz_gestion_academica"


    private const val
            CLAVE_DATOS =
        "datos_gestion"


    private lateinit var
            preferencias:
            SharedPreferences


    private var inicializado =
        false


    // -------------------------
    // REVISIONES UTP
    // -------------------------

    private val revisiones =
        mutableMapOf<
                String,
                RevisionUnidad
                >()


    // -------------------------
    // INFORMES
    // -------------------------

    private val estadosInforme =
        mutableMapOf<
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
    // UTP
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
    // INFORMES
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


            // REVISIONES UTP

            val revisionesJson =
                JSONObject()


            MockRepository
                .asignaturas
                .forEach {
                        asignatura ->


                    val revision =
                        revisionDe(
                            asignatura.id
                        )


                    val revisionJson =
                        JSONObject()


                    revisionJson.put(
                        "estado",
                        revision
                            .estado
                            .name
                    )


                    revisionJson.put(
                        "observacion",
                        revision
                            .observacion
                    )


                    revisionesJson.put(
                        asignatura.id,
                        revisionJson
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
                .forEach {
                        asignatura ->


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


            // REVISIONES

            val revisionesJson =
                raiz.optJSONObject(
                    "revisiones"
                )


            MockRepository
                .asignaturas
                .forEach {
                        asignatura ->


                    val revisionJson =
                        revisionesJson
                            ?.optJSONObject(
                                asignatura.id
                            )


                    if (
                        revisionJson != null
                    ) {

                        val estadoTexto =
                            revisionJson
                                .optString(
                                    "estado",
                                    EstadoRevisionUnidad
                                        .EN_REVISION
                                        .name
                                )


                        val estado =
                            runCatching {

                                EstadoRevisionUnidad
                                    .valueOf(
                                        estadoTexto
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
                                    revisionJson
                                        .optString(
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
                .forEach {
                        asignatura ->


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


                    val estado =
                        runCatching {

                            EstadoInformePedagogico
                                .valueOf(
                                    estadoTexto
                                )

                        }.getOrDefault(
                            EstadoInformePedagogico
                                .PENDIENTE
                        )


                    estadosInforme[
                        asignatura.id
                    ] =
                        estado
                }

        } catch (
            excepcion: Exception
        ) {

            excepcion
                .printStackTrace()
        }
    }
}