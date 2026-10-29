package com.example.mpaz_pro_6.ui.screens

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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
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
import com.example.mpaz_pro_6.model.EstadoRevisionUnidad
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.ui.theme.Paleta
import com.example.mpaz_pro_6.viewmodel.ConfiguracionUnidadViewModel


@Composable
fun PantallaConfiguracionUnidad(
    navController: NavHostController
) {

    val configuracionViewModel:
            ConfiguracionUnidadViewModel =
        viewModel()


    val uiState by
    configuracionViewModel
        .uiState
        .collectAsState()


    val asignatura =
        MockRepository
            .asignaturaDe(
                uiState.asignaturaId
            )


    val unidad =
        MockRepository
            .unidadDe(
                uiState.asignaturaId
            )


    var menuAsignaturas by remember {
        mutableStateOf(false)
    }


    var menuOa by remember {
        mutableStateOf(false)
    }


    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Paleta.Fondo
                )
                .statusBarsPadding()
                .navigationBarsPadding()
    ) {

        Row(
            modifier =
                Modifier
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
                    navController
                        .popBackStack()
                }
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ArrowBack,

                    contentDescription =
                        "Volver",

                    tint =
                        Paleta.Texto
                )
            }


            Column {

                Text(
                    text =
                        "Configuración de unidad",

                    fontSize =
                        20.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Paleta.Texto
                )


                Text(
                    text =
                        "Vista docente",

                    fontSize =
                        12.sp,

                    color =
                        Paleta.TextoSuave
                )
            }
        }


        HorizontalDivider(
            color =
                Paleta.Borde
        )


        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(20.dp)
        ) {

            // -------------------------
            // ASIGNATURA
            // -------------------------

            Text(
                text =
                    "Asignatura",

                fontWeight =
                    FontWeight.Bold,

                color =
                    Paleta.Texto
            )


            Spacer(
                Modifier.height(8.dp)
            )


            Box {

                OutlinedCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                menuAsignaturas =
                                    true
                            },

                    shape =
                        RoundedCornerShape(
                            12.dp
                        )
                ) {

                    Row(
                        modifier =
                            Modifier
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
                                    FontWeight.Bold
                            )


                            Text(
                                text =
                                    asignatura.unidad,

                                fontSize =
                                    12.sp,

                                color =
                                    Paleta.TextoSuave
                            )
                        }


                        Icon(
                            imageVector =
                                Icons.Default
                                    .ArrowDropDown,

                            contentDescription =
                                null
                        )
                    }
                }


                DropdownMenu(
                    expanded =
                        menuAsignaturas,

                    onDismissRequest = {
                        menuAsignaturas =
                            false
                    }
                ) {

                    MockRepository
                        .asignaturas
                        .forEach { opcion ->

                            DropdownMenuItem(

                                text = {

                                    Text(
                                        opcion.nombre
                                    )
                                },

                                onClick = {

                                    configuracionViewModel
                                        .seleccionarAsignatura(
                                            opcion.id
                                        )

                                    menuAsignaturas =
                                        false
                                }
                            )
                        }
                }
            }


            Spacer(
                Modifier.height(20.dp)
            )


            // -------------------------
            // UNIDAD
            // -------------------------

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults
                        .cardColors(
                            containerColor =
                                Color.White
                        ),

                shape =
                    RoundedCornerShape(
                        16.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(
                            16.dp
                        )
                ) {

                    Text(
                        text =
                            unidad.etiqueta,

                        color =
                            Paleta.Azul,

                        fontSize =
                            12.sp,

                        fontWeight =
                            FontWeight.Bold
                    )


                    Text(
                        text =
                            unidad.titulo,

                        fontSize =
                            19.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Paleta.Texto
                    )


                    Spacer(
                        Modifier.height(8.dp)
                    )


                    Text(
                        text =
                            unidad.objetivo,

                        fontSize =
                            13.sp,

                        color =
                            Paleta.TextoSuave
                    )
                }
            }


            Spacer(
                Modifier.height(24.dp)
            )


            // -------------------------
            // OA
            // -------------------------

            Text(
                text =
                    "Objetivo de aprendizaje",

                fontSize =
                    15.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Paleta.Texto
            )


            Text(
                text =
                    "Selecciona el OA que trabajará esta unidad.",

                fontSize =
                    12.sp,

                color =
                    Paleta.TextoSuave
            )


            Spacer(
                Modifier.height(8.dp)
            )


            Box {

                OutlinedCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                menuOa =
                                    true
                            },

                    shape =
                        RoundedCornerShape(
                            12.dp
                        )
                ) {

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    16.dp
                                ),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            text =
                                uiState.oaSeleccionado,

                            modifier =
                                Modifier.weight(1f),

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Paleta.Texto
                        )


                        Icon(
                            imageVector =
                                Icons.Default
                                    .ArrowDropDown,

                            contentDescription =
                                null
                        )
                    }
                }


                DropdownMenu(
                    expanded =
                        menuOa,

                    onDismissRequest = {
                        menuOa =
                            false
                    }
                ) {

                    uiState
                        .oaDisponibles
                        .forEach { oa ->

                            DropdownMenuItem(

                                text = {
                                    Text(oa)
                                },

                                onClick = {

                                    configuracionViewModel
                                        .seleccionarOa(
                                            oa
                                        )

                                    menuOa =
                                        false
                                }
                            )
                        }
                }
            }


            uiState.errorConfiguracion
                ?.let { error ->

                    Spacer(
                        Modifier.height(6.dp)
                    )


                    Text(
                        text =
                            error,

                        fontSize =
                            12.sp,

                        color =
                            MaterialTheme
                                .colorScheme
                                .error
                    )
                }


            Spacer(
                Modifier.height(24.dp)
            )


            // -------------------------
            // CONTENIDOS
            // -------------------------

            Text(
                text =
                    "Asignación académica",

                fontSize =
                    15.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Paleta.Texto
            )


            Spacer(
                Modifier.height(8.dp)
            )


            ConfiguracionSwitch(
                icono =
                    Icons.Default.MenuBook,

                titulo =
                    "Contenido explicativo",

                descripcion =
                    "${unidad.contenidos.size} contenido(s) disponibles",

                activado =
                    uiState.contenidoAsignado,

                onCambio = {

                    configuracionViewModel
                        .cambiarContenidoAsignado(
                            it
                        )
                }
            )


            Spacer(
                Modifier.height(8.dp)
            )


            ConfiguracionSwitch(
                icono =
                    Icons.Default.EmojiEvents,

                titulo =
                    "Desafío aplicado",

                descripcion =
                    "Asignar desafío antes de la prueba final",

                activado =
                    uiState.desafioAsignado,

                onCambio = {

                    configuracionViewModel
                        .cambiarDesafioAsignado(
                            it
                        )
                }
            )


            Spacer(
                Modifier.height(24.dp)
            )


            // -------------------------
            // INTENTOS
            // -------------------------

            Text(
                text =
                    "Prueba final",

                fontSize =
                    15.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Paleta.Texto
            )


            Spacer(
                Modifier.height(8.dp)
            )


            OutlinedTextField(
                value =
                    uiState.limiteIntentos,

                onValueChange = {

                    configuracionViewModel
                        .cambiarLimiteIntentos(
                            it
                        )
                },

                label = {
                    Text(
                        "Cantidad máxima de intentos"
                    )
                },

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Number
                    ),

                singleLine =
                    true,

                isError =
                    uiState.errorIntentos !=
                            null,

                trailingIcon = {

                    if (
                        uiState.errorIntentos !=
                        null
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

                supportingText = {

                    uiState.errorIntentos
                        ?.let { error ->

                            Text(
                                text =
                                    error
                            )
                        }
                },

                modifier =
                    Modifier.fillMaxWidth()
            )


            Spacer(
                Modifier.height(12.dp)
            )


            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults
                        .cardColors(
                            containerColor =
                                Paleta.AzulClaro
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
                        Modifier.width(10.dp)
                    )


                    Text(
                        text =
                            "Al guardar cambios, la unidad volverá al estado En revisión. UTP deberá autorizarla antes de que el estudiante pueda acceder.",

                        fontSize =
                            13.sp,

                        color =
                            Paleta.AzulOscuro
                    )
                }
            }


            Spacer(
                Modifier.height(16.dp)
            )


            // -------------------------
            // ESTADO UTP
            // -------------------------

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults
                        .cardColors(

                            containerColor =

                                when (
                                    uiState.estadoRevision
                                ) {

                                    EstadoRevisionUnidad
                                        .AUTORIZADA ->
                                        Paleta.VerdeClaro

                                    else ->
                                        Paleta.AmbarClaro
                                }
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
                            if (
                                uiState.estadoRevision ==
                                EstadoRevisionUnidad
                                    .AUTORIZADA
                            ) {

                                Icons.Default
                                    .CheckCircle

                            } else {

                                Icons.Default.Info
                            },

                        contentDescription =
                            null,

                        tint =
                            if (
                                uiState.estadoRevision ==
                                EstadoRevisionUnidad
                                    .AUTORIZADA
                            ) {

                                Paleta.VerdeOscuro

                            } else {

                                Paleta.AmbarOscuro
                            }
                    )


                    Spacer(
                        Modifier.width(10.dp)
                    )


                    Text(
                        text =
                            "Estado UTP: ${uiState.estadoRevision.texto}",

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Paleta.Texto
                    )
                }
            }


            uiState.mensajeGuardado
                ?.let { mensaje ->

                    Spacer(
                        Modifier.height(
                            16.dp
                        )
                    )


                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),

                        colors =
                            CardDefaults
                                .cardColors(
                                    containerColor =
                                        Paleta.VerdeClaro
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
                                    Icons.Default
                                        .CheckCircle,

                                contentDescription =
                                    null,

                                tint =
                                    Paleta.VerdeOscuro
                            )


                            Spacer(
                                Modifier.width(
                                    8.dp
                                )
                            )


                            Text(
                                text =
                                    mensaje,

                                color =
                                    Paleta.VerdeOscuro
                            )
                        }
                    }
                }


            Spacer(
                Modifier.height(24.dp)
            )


            Button(
                onClick = {

                    configuracionViewModel
                        .guardarConfiguracion()
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                colors =
                    ButtonDefaults
                        .buttonColors(
                            containerColor =
                                Paleta.Azul
                        ),

                shape =
                    RoundedCornerShape(
                        12.dp
                    )
            ) {

                Text(
                    text =
                        "Guardar y enviar a UTP",

                    fontWeight =
                        FontWeight.Bold
                )
            }


            Spacer(
                Modifier.height(24.dp)
            )
        }
    }
}


@Composable
private fun ConfiguracionSwitch(
    icono:
    androidx.compose.ui.graphics.vector.ImageVector,
    titulo: String,
    descripcion: String,
    activado: Boolean,
    onCambio: (Boolean) -> Unit
) {

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        colors =
            CardDefaults
                .cardColors(
                    containerColor =
                        Color.White
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
                    icono,

                contentDescription =
                    null,

                tint =
                    Paleta.Azul
            )


            Spacer(
                Modifier.width(12.dp)
            )


            Column(
                modifier =
                    Modifier.weight(1f)
            ) {

                Text(
                    text =
                        titulo,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Paleta.Texto
                )


                Text(
                    text =
                        descripcion,

                    fontSize =
                        12.sp,

                    color =
                        Paleta.TextoSuave
                )
            }


            Switch(
                checked =
                    activado,

                onCheckedChange =
                    onCambio
            )
        }
    }
}