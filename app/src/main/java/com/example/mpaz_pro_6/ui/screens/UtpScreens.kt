package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.EstadoRevisionUnidad
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.ui.theme.Paleta
import com.example.mpaz_pro_6.viewmodel.UtpViewModel


@Composable
fun PantallaHomeUTP(
    navController: NavHostController
) {

    val utpViewModel:
            UtpViewModel =
        viewModel()


    val uiState by
    utpViewModel
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


    var menuAbierto by remember {
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
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    20.dp
                )
    ) {

        // -------------------------
        // ENCABEZADO
        // -------------------------

        Text(
            text =
                "Revisión UTP",

            fontWeight =
                FontWeight.Bold,

            fontSize =
                22.sp,

            color =
                Paleta.Texto
        )


        Text(
            text =
                "Revisa y autoriza las unidades enviadas por el equipo docente.",

            fontSize =
                12.sp,

            color =
                Paleta.TextoSuave
        )


        Spacer(
            Modifier.height(
                20.dp
            )
        )


        // -------------------------
        // ASIGNATURA
        // -------------------------

        Text(
            text =
                "Asignatura",

            fontWeight =
                FontWeight.Bold,

            fontSize =
                15.sp,

            color =
                Paleta.Texto
        )


        Spacer(
            Modifier.height(
                8.dp
            )
        )


        Box(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            OutlinedCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable {

                            menuAbierto =
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

                    Column(
                        modifier =
                            Modifier.weight(
                                1f
                            )
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
                            "Seleccionar asignatura"
                    )
                }
            }


            DropdownMenu(
                expanded =
                    menuAbierto,

                onDismissRequest = {

                    menuAbierto =
                        false
                }
            ) {

                MockRepository
                    .asignaturas
                    .forEach {
                            opcion ->

                        DropdownMenuItem(

                            text = {

                                Column {

                                    Text(
                                        text =
                                            opcion.nombre
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

                                utpViewModel
                                    .seleccionarAsignatura(
                                        opcion.id
                                    )


                                menuAbierto =
                                    false
                            }
                        )
                    }
            }
        }


        Spacer(
            Modifier.height(
                20.dp
            )
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
                            Paleta.AzulOscuro
                    ),

            shape =
                RoundedCornerShape(
                    16.dp
                )
        ) {

            Column(
                modifier =
                    Modifier.padding(
                        20.dp
                    )
            ) {

                Text(
                    text =
                        asignatura.nombre,

                    color =
                        Color(
                            0xFFBFDBFE
                        ),

                    fontSize =
                        12.sp,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    Modifier.height(
                        4.dp
                    )
                )


                Text(
                    text =
                        unidad.titulo,

                    color =
                        Color.White,

                    fontSize =
                        21.sp,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    Modifier.height(
                        10.dp
                    )
                )


                Text(
                    text =
                        "OA seleccionado",

                    color =
                        Color.White.copy(
                            alpha = 0.7f
                        ),

                    fontSize =
                        11.sp
                )


                Text(
                    text =
                        uiState
                            .oaSeleccionado
                            .ifBlank {
                                "Sin OA seleccionado"
                            },

                    color =
                        Color.White,

                    fontSize =
                        14.sp,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    Modifier.height(
                        8.dp
                    )
                )


                Text(
                    text =
                        "Máximo ${uiState.limiteIntentos} intento(s) en prueba final",

                    color =
                        Color.White.copy(
                            alpha = 0.8f
                        ),

                    fontSize =
                        12.sp
                )
            }
        }


        Spacer(
            Modifier.height(
                16.dp
            )
        )


        // -------------------------
        // ESTADO ACTUAL
        // -------------------------

        val colorEstado =
            when (
                uiState.estado
            ) {

                EstadoRevisionUnidad
                    .AUTORIZADA ->
                    Paleta.VerdeClaro

                EstadoRevisionUnidad
                    .CAMBIOS_SOLICITADOS ->
                    Paleta.AmbarClaro

                EstadoRevisionUnidad
                    .EN_REVISION ->
                    Paleta.AzulClaro
            }


        val colorTextoEstado =
            when (
                uiState.estado
            ) {

                EstadoRevisionUnidad
                    .AUTORIZADA ->
                    Paleta.VerdeOscuro

                EstadoRevisionUnidad
                    .CAMBIOS_SOLICITADOS ->
                    Paleta.AmbarOscuro

                EstadoRevisionUnidad
                    .EN_REVISION ->
                    Paleta.AzulOscuro
            }


        Card(
            modifier =
                Modifier.fillMaxWidth(),

            colors =
                CardDefaults
                    .cardColors(
                        containerColor =
                            colorEstado
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

                        when (
                            uiState.estado
                        ) {

                            EstadoRevisionUnidad
                                .AUTORIZADA ->

                                Icons.Default
                                    .CheckCircle


                            EstadoRevisionUnidad
                                .CAMBIOS_SOLICITADOS ->

                                Icons.Default
                                    .Warning


                            EstadoRevisionUnidad
                                .EN_REVISION ->

                                Icons.Default
                                    .Info
                        },

                    contentDescription =
                        null,

                    tint =
                        colorTextoEstado
                )


                Spacer(
                    Modifier.width(
                        10.dp
                    )
                )


                Column {

                    Text(
                        text =
                            "Estado: ${uiState.estado.texto}",

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            colorTextoEstado
                    )


                    Text(
                        text =

                            if (
                                uiState
                                    .configuracionCompleta
                            ) {

                                "La unidad cumple los requisitos para revisión."

                            } else {

                                "La unidad contiene elementos pendientes."
                            },

                        fontSize =
                            11.sp,

                        color =
                            colorTextoEstado
                    )
                }
            }
        }


        Spacer(
            Modifier.height(
                20.dp
            )
        )


        // -------------------------
        // CHECKLIST
        // -------------------------

        Text(
            text =
                "Lista de verificación",

            fontWeight =
                FontWeight.Bold,

            fontSize =
                16.sp,

            color =
                Paleta.Texto
        )


        Text(
            text =
                "Los elementos se verifican automáticamente según la configuración del docente.",

            fontSize =
                12.sp,

            color =
                Paleta.TextoSuave
        )


        Spacer(
            Modifier.height(
                10.dp
            )
        )


        ItemChecklist(
            texto =

                if (
                    uiState
                        .oaSeleccionado
                        .isNotBlank()
                ) {

                    "Objetivo de aprendizaje: ${uiState.oaSeleccionado}"

                } else {

                    "Objetivo de aprendizaje no seleccionado"
                },

            cumplido =
                uiState
                    .oaSeleccionado
                    .isNotBlank()
        )


        ItemChecklist(
            texto =
                "Contenido explicativo asignado",

            cumplido =
                uiState
                    .contenidoAsignado
        )


        ItemChecklist(
            texto =
                "Actividad de práctica disponible",

            cumplido =
                uiState
                    .actividadDisponible
        )


        ItemChecklist(
            texto =
                "Desafío aplicado asignado",

            cumplido =
                uiState
                    .desafioAsignado
        )


        ItemChecklist(
            texto =
                "Prueba final configurada · ${uiState.limiteIntentos} intento(s)",

            cumplido =
                uiState
                    .pruebaConfigurada
        )


        Spacer(
            Modifier.height(
                16.dp
            )
        )


        // -------------------------
        // RESUMEN CHECKLIST
        // -------------------------

        Card(
            modifier =
                Modifier.fillMaxWidth(),

            colors =
                CardDefaults
                    .cardColors(

                        containerColor =

                            if (
                                uiState
                                    .configuracionCompleta
                            ) {

                                Paleta.VerdeClaro

                            } else {

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
                            uiState
                                .configuracionCompleta
                        ) {

                            Icons.Default
                                .CheckCircle

                        } else {

                            Icons.Default
                                .Warning
                        },

                    contentDescription =
                        null,

                    tint =

                        if (
                            uiState
                                .configuracionCompleta
                        ) {

                            Paleta.VerdeOscuro

                        } else {

                            Paleta.AmbarOscuro
                        }
                )


                Spacer(
                    Modifier.width(
                        10.dp
                    )
                )


                Text(
                    text =

                        if (
                            uiState
                                .configuracionCompleta
                        ) {

                            "Configuración académica completa."

                        } else {

                            "Faltan elementos antes de poder autorizar esta unidad."
                        },

                    fontSize =
                        13.sp,

                    fontWeight =
                        FontWeight.Medium,

                    color =

                        if (
                            uiState
                                .configuracionCompleta
                        ) {

                            Paleta.VerdeOscuro

                        } else {

                            Paleta.AmbarOscuro
                        }
                )
            }
        }


        Spacer(
            Modifier.height(
                20.dp
            )
        )


        // -------------------------
        // OBSERVACIONES
        // -------------------------

        Text(
            text =
                "Observaciones",

            fontWeight =
                FontWeight.Bold,

            fontSize =
                15.sp,

            color =
                Paleta.Texto
        )


        Spacer(
            Modifier.height(
                8.dp
            )
        )


        OutlinedTextField(
            value =
                uiState.observacion,

            onValueChange = {

                utpViewModel
                    .cambiarObservacion(
                        it
                    )
            },

            label = {

                Text(
                    "Comentario para el docente"
                )
            },

            placeholder = {

                Text(
                    "Ej.: Revisar el objetivo de aprendizaje..."
                )
            },

            minLines =
                3,

            maxLines =
                5,

            isError =
                uiState
                    .errorObservacion !=
                        null,

            trailingIcon = {

                if (
                    uiState
                        .errorObservacion !=
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

                uiState
                    .errorObservacion
                    ?.let {
                            mensaje ->

                        Text(
                            text =
                                mensaje,

                            color =
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
        // ERROR AUTORIZACIÓN
        // -------------------------

        uiState
            .errorAutorizacion
            ?.let { error ->

                Spacer(
                    Modifier.height(
                        12.dp
                    )
                )


                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    colors =
                        CardDefaults
                            .cardColors(
                                containerColor =
                                    Paleta.AmbarClaro
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
                                Icons.Default
                                    .Warning,

                            contentDescription =
                                null,

                            tint =
                                Paleta.AmbarOscuro
                        )


                        Spacer(
                            Modifier.width(
                                8.dp
                            )
                        )


                        Text(
                            text =
                                error,

                            fontSize =
                                13.sp,

                            color =
                                Paleta.AmbarOscuro
                        )
                    }
                }
            }


        // -------------------------
        // MENSAJE
        // -------------------------

        uiState.mensaje
            ?.let { mensaje ->

                Spacer(
                    Modifier.height(
                        12.dp
                    )
                )


                Text(
                    text =
                        mensaje,

                    fontWeight =
                        FontWeight.Medium,

                    color =

                        if (
                            uiState.estado ==
                            EstadoRevisionUnidad
                                .AUTORIZADA
                        ) {

                            Paleta.VerdeOscuro

                        } else {

                            Paleta.AmbarOscuro
                        }
                )
            }


        Spacer(
            Modifier.height(
                24.dp
            )
        )


        // -------------------------
        // SOLICITAR CAMBIOS
        // -------------------------

        OutlinedButton(
            onClick = {

                utpViewModel
                    .solicitarCambios()
            },

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        50.dp
                    ),

            shape =
                RoundedCornerShape(
                    12.dp
                )
        ) {

            Text(
                text =
                    "Solicitar cambios",

                color =
                    Paleta.AmbarOscuro
            )
        }


        Spacer(
            Modifier.height(
                10.dp
            )
        )


        // -------------------------
        // AUTORIZAR
        // -------------------------

        Button(
            onClick = {

                utpViewModel
                    .autorizarUnidad()
            },

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        50.dp
                    ),

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

            Icon(
                imageVector =
                    Icons.Default
                        .CheckCircle,

                contentDescription =
                    null
            )


            Spacer(
                Modifier.width(
                    8.dp
                )
            )


            Text(
                text =
                    if (
                        uiState.estado ==
                        EstadoRevisionUnidad
                            .AUTORIZADA
                    ) {

                        "Unidad autorizada"

                    } else {

                        "Autorizar unidad"
                    }
            )
        }


        Spacer(
            Modifier.height(
                24.dp
            )
        )
    }
}


@Composable
private fun ItemChecklist(
    texto: String,
    cumplido: Boolean
) {

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical =
                        4.dp
                ),

        colors =
            CardDefaults
                .cardColors(

                    containerColor =

                        if (
                            cumplido
                        ) {

                            Paleta.VerdeClaro

                        } else {

                            Paleta.AmbarClaro
                        }
                ),

        shape =
            RoundedCornerShape(
                10.dp
            )
    ) {

        Row(
            modifier =
                Modifier.padding(
                    12.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                imageVector =

                    if (
                        cumplido
                    ) {

                        Icons.Default
                            .CheckCircle

                    } else {

                        Icons.Default
                            .Warning
                    },

                contentDescription =
                    null,

                tint =

                    if (
                        cumplido
                    ) {

                        Paleta.VerdeOscuro

                    } else {

                        Paleta.AmbarOscuro
                    }
            )


            Spacer(
                Modifier.width(
                    8.dp
                )
            )


            Text(
                text =
                    texto,

                fontSize =
                    13.sp,

                fontWeight =
                    FontWeight.Medium,

                color =

                    if (
                        cumplido
                    ) {

                        Paleta.VerdeOscuro

                    } else {

                        Paleta.AmbarOscuro
                    }
            )
        }
    }
}