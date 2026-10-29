package com.example.mpaz_pro_6.model


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
    val estado: EstadoRevisionUnidad =
        EstadoRevisionUnidad.EN_REVISION,
    val observacion: String = ""
)


object GestionAcademicaRepository {

    /*
     * Estado de revisión de cada asignatura.
     *
     * Se mantiene mientras la aplicación
     * permanece abierta.
     */
    private val revisiones =
        mutableMapOf<String, RevisionUnidad>()


    fun revisionDe(
        asignaturaId: String
    ): RevisionUnidad {

        return revisiones[asignaturaId]
            ?: RevisionUnidad(
                asignaturaId =
                    asignaturaId
            )
    }


    fun autorizarUnidad(
        asignaturaId: String
    ) {

        revisiones[asignaturaId] =
            RevisionUnidad(
                asignaturaId =
                    asignaturaId,

                estado =
                    EstadoRevisionUnidad
                        .AUTORIZADA,

                observacion = ""
            )
    }


    fun solicitarCambios(
        asignaturaId: String,
        observacion: String
    ) {

        revisiones[asignaturaId] =
            RevisionUnidad(
                asignaturaId =
                    asignaturaId,

                estado =
                    EstadoRevisionUnidad
                        .CAMBIOS_SOLICITADOS,

                observacion =
                    observacion.trim()
            )
    }
}