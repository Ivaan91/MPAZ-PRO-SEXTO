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
            UtpViewModel = viewModel()


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
        modifier = Modifier
            .fillMaxSize()
            .background(Paleta.Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(20.dp)
    ) {

        // -------------------------
        // ENCABEZADO
        // -------------------------

        Text(
            text = "Revisión UTP",
            fontWeight =
                FontWeight.Bold,
            fontSize = 22.sp,
            color = Paleta.Texto
        )


        Text(
            text =
                "Revisa y autoriza las unidades enviadas por el equipo docente.",

            fontSize = 12.sp,

            color =
                Paleta.TextoSuave
        )


        Spacer(
            modifier =
                Modifier.height(20.dp)
        )


        // -------------------------
        // SELECTOR ASIGNATURA
        // -------------------------

        Text(
            text = "Asignatura",
            fontWeight =
                FontWeight.Bold,
            fontSize = 15.sp,
            color = Paleta.Texto
        )


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        Box(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            OutlinedCard(
                modifier = Modifier
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
                    .forEach { opcion ->

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
            modifier =
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
                            Paleta.AzulOscuro
                    ),

            shape =
                RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier =
                    Modifier.padding(20.dp)
            ) {

                Text(
                    text =
                        asignatura.nombre,

                    color =
                        Color(0xFFBFDBFE),

                    fontSize =
                        12.sp,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    modifier =
                        Modifier.height(4.dp)
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
                    modifier =
                        Modifier.height(8.dp)
                )


                Text(
                    text =
                        unidad.oa,

                    color =
                        Color.White,

                    fontSize =
                        13.sp
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(16.dp)
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
                RoundedCornerShape(12.dp)
        ) {

            Row(
                modifier =
                    Modifier.padding(14.dp),

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
                    modifier =
                        Modifier.width(10.dp)
                )


                Text(
                    text =
                        "Estado: ${uiState.estado.texto}",

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        colorTextoEstado
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(20.dp)
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


        Spacer(
            modifier =
                Modifier.height(10.dp)
        )


        ItemChecklist(
            texto =
                "Objetivo de aprendizaje definido"
        )


        ItemChecklist(
            texto =
                "Contenido explicativo disponible"
        )


        ItemChecklist(
            texto =
                "Actividad de práctica disponible"
        )


        ItemChecklist(
            texto =
                "Desafío y prueba final configurados"
        )


        Spacer(
            modifier =
                Modifier.height(20.dp)
        )


        // -------------------------
        // OBSERVACIÓN
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
            modifier =
                Modifier.height(8.dp)
        )


        OutlinedTextField(
            value =
                uiState.observacion,

            onValueChange = {

                utpViewModel
                    .cambiarObservacion(it)
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

            minLines = 3,

            maxLines = 5,

            isError =
                uiState.errorObservacion
                        != null,

            trailingIcon = {

                if (
                    uiState.errorObservacion
                    != null
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

                uiState.errorObservacion
                    ?.let { mensaje ->

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


        uiState.mensaje
            ?.let { mensaje ->

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
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
            modifier =
                Modifier.height(24.dp)
        )


        // -------------------------
        // SOLICITAR CAMBIOS
        // -------------------------

        OutlinedButton(
            onClick = {

                utpViewModel
                    .solicitarCambios()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            shape =
                RoundedCornerShape(12.dp)
        ) {

            Text(
                text =
                    "Solicitar cambios",

                color =
                    Paleta.AmbarOscuro
            )
        }


        Spacer(
            modifier =
                Modifier.height(10.dp)
        )


        // -------------------------
        // AUTORIZAR
        // -------------------------

        Button(
            onClick = {

                utpViewModel
                    .autorizarUnidad()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            colors =
                ButtonDefaults
                    .buttonColors(
                        containerColor =
                            Paleta.Azul
                    ),

            shape =
                RoundedCornerShape(12.dp)
        ) {

            Icon(
                imageVector =
                    Icons.Default.CheckCircle,

                contentDescription =
                    null
            )


            Spacer(
                modifier =
                    Modifier.width(8.dp)
            )


            Text(
                text =
                    "Autorizar unidad"
            )
        }


        Spacer(
            modifier =
                Modifier.height(24.dp)
        )
    }
}


@Composable
private fun ItemChecklist(
    texto: String
) {

    Row(
        modifier =
            Modifier.padding(
                vertical = 5.dp
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
                Paleta.Verde
        )


        Spacer(
            modifier =
                Modifier.width(8.dp)
        )


        Text(
            text =
                texto,

            fontSize =
                14.sp,

            color =
                Paleta.Texto
        )
    }
}