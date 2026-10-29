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

    // -------------------------
    // REVISIONES UTP
    // -------------------------

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


    // -------------------------
    // INFORMES PEDAGÓGICOS
    // -------------------------

    private val estadosInforme =
        mutableMapOf<
                String,
                EstadoInformePedagogico
                >()


    fun estadoInforme(
        asignaturaId: String
    ): EstadoInformePedagogico {

        return estadosInforme[asignaturaId]
            ?: EstadoInformePedagogico.PENDIENTE
    }


    fun validarInforme(
        asignaturaId: String
    ) {

        estadosInforme[asignaturaId] =
            EstadoInformePedagogico.VALIDADO
    }
}