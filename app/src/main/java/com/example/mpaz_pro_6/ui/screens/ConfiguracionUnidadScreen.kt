package com.example.mpaz_pro_6.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.ui.theme.Paleta
import com.example.mpaz_pro_6.viewmodel.ConfiguracionUnidadViewModel


@Composable
fun PantallaConfiguracionUnidad(
    navController: NavHostController
) {

    val configuracionViewModel:
            ConfiguracionUnidadViewModel = viewModel()

    val uiState by
    configuracionViewModel.uiState.collectAsState()


    val asignatura =
        MockRepository.asignaturaDe(
            uiState.asignaturaId
        )

    val unidad =
        MockRepository.unidadDe(
            uiState.asignaturaId
        )


    // Estado visual para abrir/cerrar
    // el selector de asignaturas.
    var menuAsignaturasAbierto by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Paleta.Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        // -------------------------
        // ENCABEZADO
        // -------------------------

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Paleta.Texto
                )
            }


            Column {

                Text(
                    text = "Configuración de unidad",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Paleta.Texto
                )

                Text(
                    text = "Vista docente",
                    fontSize = 12.sp,
                    color = Paleta.TextoSuave
                )
            }
        }


        HorizontalDivider(
            color = Paleta.Borde
        )


        // -------------------------
        // CONTENIDO
        // -------------------------

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            Text(
                text = "Asignatura",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Paleta.Texto
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // -------------------------
            // SELECTOR DE ASIGNATURA
            // -------------------------

            Box(
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            menuAsignaturasAbierto =
                                true
                        },

                    shape =
                        RoundedCornerShape(12.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Column(
                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Text(
                                text =
                                    asignatura.nombre,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Paleta.Texto
                            )


                            Text(
                                text =
                                    asignatura.unidad,

                                fontSize = 12.sp,

                                color =
                                    Paleta.TextoSuave
                            )
                        }


                        Icon(
                            imageVector =
                                Icons.Default.ArrowDropDown,

                            contentDescription =
                                "Seleccionar asignatura"
                        )
                    }
                }


                DropdownMenu(
                    expanded =
                        menuAsignaturasAbierto,

                    onDismissRequest = {

                        menuAsignaturasAbierto =
                            false
                    }
                ) {

                    MockRepository
                        .asignaturas
                        .forEach { opcion ->

                            DropdownMenuItem(

                                text = {

                                    Column {

                                        Text(
                                            text =
                                                opcion.nombre,

                                            fontWeight =
                                                FontWeight.Medium
                                        )


                                        Text(
                                            text =
                                                opcion.unidad,

                                            fontSize =
                                                11.sp,

                                            color =
                                                Paleta.TextoSuave
                                        )
                                    }
                                },


                                onClick = {

                                    configuracionViewModel
                                        .seleccionarAsignatura(
                                            opcion.id
                                        )

                                    menuAsignaturasAbierto =
                                        false
                                }
                            )
                        }
                }
            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // -------------------------
            // INFORMACIÓN DE UNIDAD
            // -------------------------

            Text(
                text = "Unidad seleccionada",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Paleta.Texto
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            /*
             * AnimatedContent:
             *
             * Cada vez que cambia la asignatura,
             * esta tarjeta hace un pequeño fade.
             */
            AnimatedContent(

                targetState =
                    uiState.asignaturaId,

                transitionSpec = {

                    fadeIn(
                        animationSpec =
                            tween(250)
                    ) togetherWith

                            fadeOut(
                                animationSpec =
                                    tween(180)
                            )
                },

                label =
                    "animacion_unidad"
            ) {

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.White
                        ),

                    shape =
                        RoundedCornerShape(16.dp)
                ) {

                    Column(
                        modifier =
                            Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = unidad.etiqueta,
                            color = Paleta.Azul,
                            fontSize = 12.sp,
                            fontWeight =
                                FontWeight.Bold
                        )


                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )


                        Text(
                            text = unidad.titulo,
                            fontSize = 18.sp,
                            fontWeight =
                                FontWeight.Bold,
                            color = Paleta.Texto
                        )


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        Text(
                            text =
                                "Objetivo de aprendizaje",

                            fontSize = 12.sp,

                            color =
                                Paleta.TextoSuave
                        )


                        Text(
                            text = unidad.oa,

                            fontWeight =
                                FontWeight.Bold,

                            color = Paleta.Texto
                        )


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        Text(
                            text = "Objetivo",
                            fontSize = 12.sp,
                            color =
                                Paleta.TextoSuave
                        )


                        Text(
                            text =
                                unidad.objetivo,

                            fontSize = 14.sp,

                            color =
                                Paleta.Texto
                        )


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        Text(
                            text =
                                "${unidad.contenidos.size} contenido(s) disponibles",

                            fontSize = 12.sp,

                            color =
                                Paleta.TextoSuave
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // -------------------------
            // CONFIGURACIÓN DE PRUEBA
            // -------------------------

            Text(
                text = "Prueba final",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Paleta.Texto
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            OutlinedTextField(
                value =
                    uiState.limiteIntentos,

                onValueChange = {

                    configuracionViewModel
                        .cambiarLimiteIntentos(it)
                },

                label = {

                    Text(
                        "Cantidad máxima de intentos"
                    )
                },

                singleLine = true,

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Number
                    ),

                isError =
                    uiState.errorIntentos != null,

                trailingIcon = {

                    if (
                        uiState.errorIntentos != null
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Error,

                            contentDescription =
                                "Error",

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .error
                        )
                    }
                },

                modifier =
                    Modifier.fillMaxWidth()
            )


            // -------------------------
            // ERROR ANIMADO
            // -------------------------

            AnimatedContent(

                targetState =
                    uiState.errorIntentos,

                transitionSpec = {

                    (
                            fadeIn(
                                animationSpec =
                                    tween(200)
                            ) +

                                    expandVertically(
                                        animationSpec =
                                            tween(250)
                                    )
                            ) togetherWith

                            (
                                    fadeOut(
                                        animationSpec =
                                            tween(150)
                                    ) +

                                            shrinkVertically(
                                                animationSpec =
                                                    tween(200)
                                            )
                                    )
                },

                label =
                    "animacion_error_intentos"
            ) { mensajeError ->

                if (mensajeError != null) {

                    Text(
                        text =
                            mensajeError,

                        color =
                            MaterialTheme
                                .colorScheme
                                .error,

                        fontSize = 12.sp,

                        modifier =
                            Modifier.padding(
                                start = 16.dp,
                                top = 6.dp
                            )
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // -------------------------
            // INFORMACIÓN DE AYUDA
            // -------------------------

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Paleta.AzulClaro
                    ),

                shape =
                    RoundedCornerShape(12.dp)
            ) {

                Row(
                    modifier =
                        Modifier.padding(14.dp),

                    verticalAlignment =
                        Alignment.Top
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Info,

                        contentDescription =
                            null,

                        tint =
                            Paleta.Azul
                    )


                    Spacer(
                        modifier =
                            Modifier.width(10.dp)
                    )


                    Text(
                        text =
                            "El estudiante podrá realizar la prueba final entre 1 y 10 veces. " +
                                    "Cuando alcance el límite, ya no podrá iniciar un nuevo intento.",

                        fontSize = 13.sp,

                        color =
                            Paleta.AzulOscuro
                    )
                }
            }


            // -------------------------
            // MENSAJE DE ÉXITO ANIMADO
            // -------------------------

            AnimatedContent(

                targetState =
                    uiState.mensajeGuardado,

                transitionSpec = {

                    (
                            fadeIn(
                                animationSpec =
                                    tween(250)
                            ) +

                                    expandVertically(
                                        animationSpec =
                                            tween(300)
                                    )
                            ) togetherWith

                            (
                                    fadeOut(
                                        animationSpec =
                                            tween(150)
                                    ) +

                                            shrinkVertically(
                                                animationSpec =
                                                    tween(200)
                                            )
                                    )
                },

                label =
                    "animacion_guardado"
            ) { mensaje ->

                if (mensaje != null) {

                    Column {

                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )


                        Card(
                            modifier =
                                Modifier.fillMaxWidth(),

                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        Paleta.VerdeClaro
                                ),

                            shape =
                                RoundedCornerShape(
                                    12.dp
                                )
                        ) {

                            Row(
                                modifier =
                                    Modifier.padding(
                                        14.dp
                                    ),

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.CheckCircle,

                                    contentDescription =
                                        null,

                                    tint =
                                        Paleta.VerdeOscuro
                                )


                                Spacer(
                                    modifier =
                                        Modifier.width(
                                            10.dp
                                        )
                                )


                                Text(
                                    text = mensaje,

                                    color =
                                        Paleta.VerdeOscuro,

                                    fontWeight =
                                        FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // -------------------------
            // GUARDAR
            // -------------------------

            Button(
                onClick = {

                    configuracionViewModel
                        .guardarConfiguracion()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Paleta.Azul,

                        contentColor =
                            Color.White
                    ),

                shape =
                    RoundedCornerShape(12.dp)
            ) {

                Text(
                    text =
                        "Guardar configuración",

                    fontWeight =
                        FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}