package com.example.mpaz_pro_6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mpaz_pro_6.data.UsuarioDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class LoginUiState(
    val usuario: String = "",
    val clave: String = "",

    val errorUsuario: String? = null,
    val errorClave: String? = null,
    val errorGeneral: String? = null,

    val cargando: Boolean = false
)


class LoginViewModel(
    private val usuarioDao: UsuarioDao
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> =
        _uiState.asStateFlow()


    fun cambiarUsuario(nuevoUsuario: String) {

        _uiState.value = _uiState.value.copy(
            usuario = nuevoUsuario,
            errorUsuario = null,
            errorGeneral = null
        )
    }

    fun cambiarClave(nuevaClave: String) {

        _uiState.value = _uiState.value.copy(
            clave = nuevaClave,
            errorClave = null,
            errorGeneral = null
        )
    }


    fun validarCampos(): Boolean {

        val estado = _uiState.value

        val errorUsuario =
            if (estado.usuario.isBlank()) {
                "Debes ingresar un usuario."
            } else {
                null
            }

        val errorClave =
            if (estado.clave.isBlank()) {
                "Debes ingresar una clave."
            } else {
                null
            }

        _uiState.value = estado.copy(
            errorUsuario = errorUsuario,
            errorClave = errorClave,
            errorGeneral = null
        )

        return errorUsuario == null &&
                errorClave == null
    }


    fun iniciarSesion(
        onLoginCorrecto: (String) -> Unit
    ) {

        if (!validarCampos()) {
            return
        }

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    cargando = true,
                    errorGeneral = null
                )
            val estado = _uiState.value

            val usuarioEncontrado =
                usuarioDao.validarLogin(
                    estado.usuario.trim(),
                    estado.clave
                )

            if (usuarioEncontrado == null) {

                _uiState.value =
                    _uiState.value.copy(
                        cargando = false,
                        errorGeneral = "Usuario o clave incorrectos."
                    )

                return@launch
            }

            _uiState.value =
                _uiState.value.copy(
                    cargando = false,
                    errorGeneral = null
                )

            onLoginCorrecto(
                usuarioEncontrado.rol
            )
        }
    }
}


class LoginViewModelFactory(
    private val usuarioDao: UsuarioDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return LoginViewModel(usuarioDao) as T
    }
}