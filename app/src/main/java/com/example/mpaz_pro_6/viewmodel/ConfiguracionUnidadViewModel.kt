package com.example.mpaz_pro_6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mpaz_pro_6.model.EstadoRevisionUnidad
import com.example.mpaz_pro_6.model.GestionAcademicaRepository
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class ConfiguracionUnidadUiState(

    val asignaturaId:
    String = "3",

    val oaDisponibles:
    List<String> =
        emptyList(),

    val oaSeleccionado:
    String = "",

    val contenidoAsignado:
    Boolean = true,

    val desafioAsignado:
    Boolean = true,

    val limiteIntentos:
    String = "3",

    val estadoRevision:
    EstadoRevisionUnidad =
        EstadoRevisionUnidad.EN_REVISION,

    val errorIntentos:
    String? = null,

    val errorConfiguracion:
    String? = null,

    val mensajeGuardado:
    String? = null
)


class ConfiguracionUnidadViewModel :
    ViewModel() {


    private val _uiState =
        MutableStateFlow(
            cargarEstado(
                "3"
            )
        )


    val uiState:
            StateFlow<ConfiguracionUnidadUiState> =
        _uiState.asStateFlow()


    private fun oaDisponibles(
        asignaturaId: String
    ): List<String> {

        return MockRepository
            .unidadDe(
                asignaturaId
            )
            .oa
            .split(" · ")
            .map {
                it.trim()
            }
            .filter {
                it.isNotBlank()
            }
    }


    private fun cargarEstado(
        asignaturaId: String
    ): ConfiguracionUnidadUiState {

        val configuracion =
            GestionAcademicaRepository
                .configuracionDe(
                    asignaturaId
                )


        val disponibles =
            oaDisponibles(
                asignaturaId
            )


        val oa =
            if (
                configuracion
                    .oaSeleccionado in
                disponibles
            ) {

                configuracion
                    .oaSeleccionado

            } else {

                disponibles
                    .firstOrNull()
                    .orEmpty()
            }


        return ConfiguracionUnidadUiState(

            asignaturaId =
                asignaturaId,

            oaDisponibles =
                disponibles,

            oaSeleccionado =
                oa,

            contenidoAsignado =
                configuracion
                    .contenidoAsignado,

            desafioAsignado =
                configuracion
                    .desafioAsignado,

            limiteIntentos =
                ProgresoRepository
                    .limiteIntentos(
                        asignaturaId
                    )
                    .toString(),

            estadoRevision =
                GestionAcademicaRepository
                    .revisionDe(
                        asignaturaId
                    )
                    .estado
        )
    }


    fun seleccionarAsignatura(
        asignaturaId: String
    ) {

        _uiState.value =
            cargarEstado(
                asignaturaId
            )
    }


    fun seleccionarOa(
        oa: String
    ) {

        _uiState.value =
            _uiState.value.copy(

                oaSeleccionado =
                    oa,

                errorConfiguracion =
                    null,

                mensajeGuardado =
                    null
            )
    }


    fun cambiarContenidoAsignado(
        asignado: Boolean
    ) {

        _uiState.value =
            _uiState.value.copy(

                contenidoAsignado =
                    asignado,

                mensajeGuardado =
                    null
            )
    }


    fun cambiarDesafioAsignado(
        asignado: Boolean
    ) {

        _uiState.value =
            _uiState.value.copy(

                desafioAsignado =
                    asignado,

                mensajeGuardado =
                    null
            )
    }


    fun cambiarLimiteIntentos(
        valor: String
    ) {

        val valorLimpio =
            valor
                .filter {
                    it.isDigit()
                }
                .take(2)


        _uiState.value =
            _uiState.value.copy(

                limiteIntentos =
                    valorLimpio,

                errorIntentos =
                    null,

                mensajeGuardado =
                    null
            )
    }


    fun guardarConfiguracion():
            Boolean {

        val estado =
            _uiState.value


        if (
            estado
                .oaSeleccionado
                .isBlank()
        ) {

            _uiState.value =
                estado.copy(

                    errorConfiguracion =
                        "Debes seleccionar un objetivo de aprendizaje.",

                    mensajeGuardado =
                        null
                )


            return false
        }


        val limite =
            estado
                .limiteIntentos
                .toIntOrNull()


        if (
            estado
                .limiteIntentos
                .isBlank()
        ) {

            _uiState.value =
                estado.copy(

                    errorIntentos =
                        "Debes ingresar la cantidad de intentos.",

                    mensajeGuardado =
                        null
                )


            return false
        }


        if (
            limite == null ||
            limite !in 1..10
        ) {

            _uiState.value =
                estado.copy(

                    errorIntentos =
                        "La cantidad de intentos debe estar entre 1 y 10.",

                    mensajeGuardado =
                        null
                )


            return false
        }


        ProgresoRepository
            .configurarLimiteIntentos(

                asignaturaId =
                    estado.asignaturaId,

                limite =
                    limite
            )


        GestionAcademicaRepository
            .guardarConfiguracionUnidad(

                asignaturaId =
                    estado.asignaturaId,

                oaSeleccionado =
                    estado.oaSeleccionado,

                contenidoAsignado =
                    estado.contenidoAsignado,

                desafioAsignado =
                    estado.desafioAsignado
            )


        _uiState.value =
            estado.copy(

                estadoRevision =
                    EstadoRevisionUnidad
                        .EN_REVISION,

                errorIntentos =
                    null,

                errorConfiguracion =
                    null,

                mensajeGuardado =
                    "Configuración guardada y enviada a revisión UTP."
            )


        return true
    }
}