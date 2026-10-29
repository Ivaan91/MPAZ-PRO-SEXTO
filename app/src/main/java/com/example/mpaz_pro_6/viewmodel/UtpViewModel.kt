package com.example.mpaz_pro_6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mpaz_pro_6.model.EstadoRevisionUnidad
import com.example.mpaz_pro_6.model.GestionAcademicaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class UtpUiState(

    val asignaturaId: String = "3",

    val estado:
    EstadoRevisionUnidad =
        EstadoRevisionUnidad.EN_REVISION,

    val observacion: String = "",

    val errorObservacion:
    String? = null,

    val mensaje:
    String? = null
)


class UtpViewModel : ViewModel() {

    private val revisionInicial =
        GestionAcademicaRepository
            .revisionDe("3")


    private val _uiState =
        MutableStateFlow(

            UtpUiState(
                asignaturaId = "3",
                estado =
                    revisionInicial.estado,
                observacion =
                    revisionInicial.observacion
            )
        )


    val uiState:
            StateFlow<UtpUiState> =
        _uiState.asStateFlow()


    fun seleccionarAsignatura(
        asignaturaId: String
    ) {

        val revision =
            GestionAcademicaRepository
                .revisionDe(
                    asignaturaId
                )


        _uiState.value =
            UtpUiState(

                asignaturaId =
                    asignaturaId,

                estado =
                    revision.estado,

                observacion =
                    revision.observacion
            )
    }


    fun cambiarObservacion(
        valor: String
    ) {

        _uiState.value =
            _uiState.value.copy(

                observacion =
                    valor,

                errorObservacion =
                    null,

                mensaje =
                    null
            )
    }


    fun autorizarUnidad() {

        val asignaturaId =
            _uiState.value
                .asignaturaId


        GestionAcademicaRepository
            .autorizarUnidad(
                asignaturaId
            )


        _uiState.value =
            _uiState.value.copy(

                estado =
                    EstadoRevisionUnidad
                        .AUTORIZADA,

                observacion = "",

                errorObservacion =
                    null,

                mensaje =
                    "Unidad autorizada correctamente."
            )
    }


    fun solicitarCambios(): Boolean {

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

                mensaje =
                    "Solicitud de cambios registrada."
            )


        return true
    }
}