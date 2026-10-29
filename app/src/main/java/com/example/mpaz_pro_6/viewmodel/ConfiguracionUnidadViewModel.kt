package com.example.mpaz_pro_6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mpaz_pro_6.model.ProgresoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class ConfiguracionUnidadUiState(
    val asignaturaId: String = "3",
    val limiteIntentos: String = "3",
    val errorIntentos: String? = null,
    val mensajeGuardado: String? = null
)


class ConfiguracionUnidadViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            ConfiguracionUnidadUiState(
                asignaturaId = "3",
                limiteIntentos = ProgresoRepository
                    .limiteIntentos("3")
                    .toString()
            )
        )

    val uiState: StateFlow<ConfiguracionUnidadUiState> =
        _uiState.asStateFlow()


    fun seleccionarAsignatura(asignaturaId: String) {

        val limiteActual =
            ProgresoRepository.limiteIntentos(asignaturaId)

        _uiState.value =
            _uiState.value.copy(
                asignaturaId = asignaturaId,
                limiteIntentos = limiteActual.toString(),
                errorIntentos = null,
                mensajeGuardado = null
            )
    }


    fun cambiarLimiteIntentos(valor: String) {

        // Solo permitimos números.
        val valorLimpio =
            valor.filter { it.isDigit() }.take(2)

        _uiState.value =
            _uiState.value.copy(
                limiteIntentos = valorLimpio,
                errorIntentos = null,
                mensajeGuardado = null
            )
    }


    fun guardarConfiguracion(): Boolean {

        val estado = _uiState.value

        val limite =
            estado.limiteIntentos.toIntOrNull()

        if (estado.limiteIntentos.isBlank()) {

            _uiState.value =
                estado.copy(
                    errorIntentos =
                        "Debes ingresar la cantidad de intentos.",
                    mensajeGuardado = null
                )

            return false
        }


        if (limite == null || limite !in 1..10) {

            _uiState.value =
                estado.copy(
                    errorIntentos =
                        "La cantidad de intentos debe estar entre 1 y 10.",
                    mensajeGuardado = null
                )

            return false
        }


        ProgresoRepository.configurarLimiteIntentos(
            asignaturaId = estado.asignaturaId,
            limite = limite
        )


        _uiState.value =
            estado.copy(
                errorIntentos = null,
                mensajeGuardado =
                    "Configuración guardada correctamente."
            )


        return true
    }
}