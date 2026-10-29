package com.example.mpaz_pro_6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mpaz_pro_6.model.EstadoRevisionUnidad
import com.example.mpaz_pro_6.model.Etapa
import com.example.mpaz_pro_6.model.GestionAcademicaRepository
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class UtpUiState(

    val asignaturaId: String = "3",

    val estado:
    EstadoRevisionUnidad =
        EstadoRevisionUnidad.EN_REVISION,

    val oaSeleccionado:
    String = "",

    val contenidoAsignado:
    Boolean = false,

    val actividadDisponible:
    Boolean = false,

    val desafioAsignado:
    Boolean = false,

    val pruebaConfigurada:
    Boolean = false,

    val limiteIntentos:
    Int = 3,

    val configuracionCompleta:
    Boolean = false,

    val observacion:
    String = "",

    val errorObservacion:
    String? = null,

    val errorAutorizacion:
    String? = null,

    val mensaje:
    String? = null
)


class UtpViewModel :
    ViewModel() {


    private val _uiState =
        MutableStateFlow(
            cargarEstado(
                "3"
            )
        )


    val uiState:
            StateFlow<UtpUiState> =
        _uiState.asStateFlow()


    // -------------------------
    // CARGAR UNIDAD
    // -------------------------

    private fun cargarEstado(
        asignaturaId: String
    ): UtpUiState {

        val revision =
            GestionAcademicaRepository
                .revisionDe(
                    asignaturaId
                )


        val configuracion =
            GestionAcademicaRepository
                .configuracionDe(
                    asignaturaId
                )


        val actividadDisponible =
            MockRepository
                .preguntasDe(
                    asignaturaId,
                    Etapa.ACTIVIDAD
                )
                .isNotEmpty()


        val desafioDisponible =
            MockRepository
                .preguntasDe(
                    asignaturaId,
                    Etapa.DESAFIO
                )
                .isNotEmpty()


        val pruebaDisponible =
            MockRepository
                .preguntasDe(
                    asignaturaId,
                    Etapa.PRUEBA
                )
                .isNotEmpty()


        val limiteIntentos =
            ProgresoRepository
                .limiteIntentos(
                    asignaturaId
                )


        val contenidoOk =
            configuracion
                .contenidoAsignado &&
                    MockRepository
                        .unidadDe(
                            asignaturaId
                        )
                        .contenidos
                        .isNotEmpty()


        val desafioOk =
            configuracion
                .desafioAsignado &&
                    desafioDisponible


        val pruebaOk =
            pruebaDisponible &&
                    limiteIntentos in 1..10


        val completa =
            configuracion
                .oaSeleccionado
                .isNotBlank() &&
                    contenidoOk &&
                    actividadDisponible &&
                    desafioOk &&
                    pruebaOk


        return UtpUiState(

            asignaturaId =
                asignaturaId,

            estado =
                revision.estado,

            oaSeleccionado =
                configuracion
                    .oaSeleccionado,

            contenidoAsignado =
                contenidoOk,

            actividadDisponible =
                actividadDisponible,

            desafioAsignado =
                desafioOk,

            pruebaConfigurada =
                pruebaOk,

            limiteIntentos =
                limiteIntentos,

            configuracionCompleta =
                completa,

            observacion =
                revision.observacion
        )
    }


    // -------------------------
    // CAMBIAR ASIGNATURA
    // -------------------------

    fun seleccionarAsignatura(
        asignaturaId: String
    ) {

        _uiState.value =
            cargarEstado(
                asignaturaId
            )
    }


    // -------------------------
    // OBSERVACIÓN
    // -------------------------

    fun cambiarObservacion(
        valor: String
    ) {

        _uiState.value =
            _uiState.value.copy(

                observacion =
                    valor,

                errorObservacion =
                    null,

                errorAutorizacion =
                    null,

                mensaje =
                    null
            )
    }


    // -------------------------
    // AUTORIZAR
    // -------------------------

    fun autorizarUnidad():
            Boolean {

        val estadoActual =
            _uiState.value


        if (
            !estadoActual
                .configuracionCompleta
        ) {

            _uiState.value =
                estadoActual.copy(

                    errorAutorizacion =
                        "No puedes autorizar la unidad porque todavía existen elementos pendientes en la configuración académica.",

                    mensaje =
                        null
                )


            return false
        }


        GestionAcademicaRepository
            .autorizarUnidad(
                estadoActual
                    .asignaturaId
            )


        _uiState.value =
            cargarEstado(
                estadoActual
                    .asignaturaId
            ).copy(

                estado =
                    EstadoRevisionUnidad
                        .AUTORIZADA,

                observacion =
                    "",

                errorObservacion =
                    null,

                errorAutorizacion =
                    null,

                mensaje =
                    "Unidad autorizada correctamente."
            )


        return true
    }


    // -------------------------
    // SOLICITAR CAMBIOS
    // -------------------------

    fun solicitarCambios():
            Boolean {

        val estadoActual =
            _uiState.value


        if (
            estadoActual
                .observacion
                .isBlank()
        ) {

            _uiState.value =
                estadoActual.copy(

                    errorObservacion =
                        "Debes indicar qué cambios debe realizar el docente.",

                    errorAutorizacion =
                        null,

                    mensaje =
                        null
                )


            return false
        }


        GestionAcademicaRepository
            .solicitarCambios(

                asignaturaId =
                    estadoActual
                        .asignaturaId,

                observacion =
                    estadoActual
                        .observacion
            )


        _uiState.value =
            estadoActual.copy(

                estado =
                    EstadoRevisionUnidad
                        .CAMBIOS_SOLICITADOS,

                errorObservacion =
                    null,

                errorAutorizacion =
                    null,

                mensaje =
                    "Solicitud de cambios registrada."
            )


        return true
    }
}