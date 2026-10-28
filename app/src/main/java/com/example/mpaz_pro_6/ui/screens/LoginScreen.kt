package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.data.UsuarioDao
import com.example.mpaz_pro_6.data.UsuarioEntity
import com.example.mpaz_pro_6.viewmodel.LoginViewModel
import com.example.mpaz_pro_6.viewmodel.LoginViewModelFactory


@Composable
fun PantallaLogin(
    navController: NavHostController,
    usuarioDao: UsuarioDao
) {

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(usuarioDao)
    )

    val uiState by loginViewModel.uiState.collectAsState()

    var usuarios by remember {
        mutableStateOf<List<UsuarioEntity>>(emptyList())
    }

    var claveVisible by remember {
        mutableStateOf(false)
    }

    var mostrarCuentas by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(Unit) {

        var lista = usuarioDao.obtenerTodosLosUsuarios()

        if (lista.isEmpty()) {

            usuarioDao.insertarUsuarios(
                listOf(
                    UsuarioEntity(
                        iniciales = "SC",
                        nombre = "Sofía Contreras",
                        descripcion = "Estudiante · 6.º A",
                        rol = "Estudiante",
                        usuario = "sofia",
                        clave = "sofia123"
                    ),

                    UsuarioEntity(
                        iniciales = "JM",
                        nombre = "Javier Muñoz",
                        descripcion = "Profesor jefe · 6.º A",
                        rol = "Docente",
                        usuario = "javier",
                        clave = "javier123"
                    ),

                    UsuarioEntity(
                        iniciales = "AV",
                        nombre = "Andrea Vidal",
                        descripcion = "Unidad Técnico Pedagógica",
                        rol = "UTP",
                        usuario = "andrea",
                        clave = "andrea123"
                    )
                )
            )

            lista = usuarioDao.obtenerTodosLosUsuarios()
        }

        usuarios = lista
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {

        // Encabezado azul.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(
                    color = Color(0xFF1E3A8A),
                    shape = RoundedCornerShape(
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp
                    )
                )
                .padding(24.dp)
        ) {

            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {

                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = "Logo",
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "MPAZ PRO 6.º",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Escuela Escritora Marcela Paz - Apoyo didáctico",
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {

            Text(
                text = "Iniciar sesión",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Ingresa tu usuario y tu clave.",
                fontSize = 13.sp,
                color = Color.Gray
            )


            Spacer(
                modifier = Modifier.height(16.dp)
            )


            // CAMPO USUARIO
            OutlinedTextField(
                value = uiState.usuario,

                onValueChange = {
                    loginViewModel.cambiarUsuario(it)
                },

                label = {
                    Text("Usuario")
                },

                singleLine = true,

                // Si existe un error de usuario,
                // Material 3 muestra el campo en estado de error.
                isError = uiState.errorUsuario != null,

                trailingIcon = {

                    if (uiState.errorUsuario != null) {

                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error en usuario",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },

                supportingText = {

                    uiState.errorUsuario?.let { mensaje ->

                        Text(
                            text = mensaje,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },

                modifier = Modifier.fillMaxWidth()
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // CAMPO CLAVE
            OutlinedTextField(
                value = uiState.clave,

                onValueChange = {
                    loginViewModel.cambiarClave(it)
                },

                label = {
                    Text("Clave")
                },

                singleLine = true,

                isError = uiState.errorClave != null,

                visualTransformation =
                    if (claveVisible) {

                        VisualTransformation.None

                    } else {

                        PasswordVisualTransformation()
                    },

                trailingIcon = {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Ícono de error.
                        if (uiState.errorClave != null) {

                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Error en clave",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }


                        // Mostrar / ocultar contraseña.
                        IconButton(
                            onClick = {
                                claveVisible = !claveVisible
                            }
                        ) {

                            Icon(
                                imageVector =
                                    if (claveVisible) {

                                        Icons.Default.VisibilityOff

                                    } else {

                                        Icons.Default.Visibility
                                    },

                                contentDescription =
                                    if (claveVisible) {

                                        "Ocultar clave"

                                    } else {

                                        "Mostrar clave"
                                    }
                            )
                        }
                    }
                },

                supportingText = {

                    uiState.errorClave?.let { mensaje ->

                        Text(
                            text = mensaje,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },

                modifier = Modifier.fillMaxWidth()
            )


            // ERROR GENERAL:
            // usuario y contraseña escritos,
            // pero las credenciales no son correctas.
            uiState.errorGeneral?.let { mensaje ->

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp
                )
            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // BOTÓN INGRESAR
            Button(
                onClick = {

                    loginViewModel.iniciarSesion { rol ->

                        when (rol) {

                            "Estudiante" -> {
                                navController.navigate(
                                    "home_estudiante"
                                )
                            }

                            "Docente" -> {
                                navController.navigate(
                                    "home_docente"
                                )
                            }

                            "UTP" -> {
                                navController.navigate(
                                    "home_utp"
                                )
                            }
                        }
                    }
                },

                // Lo dejamos habilitado aunque los campos
                // estén vacíos para poder mostrar los errores.
                enabled = !uiState.cargando,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2563EB)
                ),

                shape = RoundedCornerShape(12.dp)
            ) {

                if (uiState.cargando) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                } else {

                    Text("Ingresar")
                }
            }


            Spacer(
                modifier = Modifier.height(12.dp)
            )


            // BOTÓN CUENTAS DE DEMOSTRACIÓN
            TextButton(
                onClick = {
                    mostrarCuentas = !mostrarCuentas
                }
            ) {

                Text(
                    text = "Cuentas de demostración",
                    color = Color(0xFF2563EB)
                )

                Icon(
                    imageVector =
                        if (mostrarCuentas) {

                            Icons.Default.KeyboardArrowUp

                        } else {

                            Icons.Default.KeyboardArrowDown
                        },

                    contentDescription = null,
                    tint = Color(0xFF2563EB)
                )
            }


            // LISTA DE CUENTAS
            if (mostrarCuentas) {

                Card(
                    modifier = Modifier.fillMaxWidth(),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                    shape = RoundedCornerShape(12.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(
                            vertical = 8.dp
                        )
                    ) {

                        Text(
                            text = "Toca una cuenta para completar los campos.",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 4.dp
                            )
                        )


                        usuarios.forEach { user ->

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {

                                        // Los valores ahora se modifican
                                        // mediante el ViewModel.
                                        loginViewModel.cambiarUsuario(
                                            user.usuario
                                        )

                                        loginViewModel.cambiarClave(
                                            user.clave
                                        )

                                        claveVisible = false
                                    }
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    )
                            ) {

                                Text(
                                    text = "${user.rol} · ${user.nombre}",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )

                                Text(
                                    text = "${user.usuario} / ${user.clave}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Text(
                text = "Demostración con datos ficticios · Sin permisos del dispositivo",
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}