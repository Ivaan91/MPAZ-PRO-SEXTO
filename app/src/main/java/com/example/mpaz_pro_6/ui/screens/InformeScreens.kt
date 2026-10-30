package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
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
import com.example.mpaz_pro_6.model.EstadoInformePedagogico
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.ui.theme.Paleta
import com.example.mpaz_pro_6.viewmodel.InformePedagogicoUiState
import com.example.mpaz_pro_6.viewmodel.InformePedagogicoViewModel


@Composable
fun PantallaInformePedagogico(
    navController: NavHostController
) {

    val informeViewModel:
            InformePedagogicoViewModel =
        viewModel()


    val uiState by
    informeViewModel
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


    /*
     * Cada vez que entramos al informe,
     * actualizamos sus datos usando el
     * progreso actual de Sofía.
     */
    LaunchedEffect(Unit) {

        informeViewModel
            .actualizar()
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
                        "Informe pedagógico",

                    fontWeight =
                        FontWeight.Bold,

                    fontSize =
                        20.sp,

                    color =
                        Paleta.Texto
                )


                Text(
                    text =
                        "Vista exclusiva del equipo docente",

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
            modifier = Modifier
                .weight(1f)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            // -------------------------
            // ASIGNATURA / UNIDAD
            // -------------------------

            Text(
                text =
                    asignatura.nombre,

                fontSize =
                    13.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Paleta.Azul
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


            Text(
                text =
                    unidad.oa,

                fontSize =
                    12.sp,

                color =
                    Paleta.TextoSuave
            )
            Spacer(modifier = Modifier.height(12.dp))
            FichaInforme(uiState)


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            // -------------------------
            // RESUMEN PRINCIPAL
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
                        Modifier.padding(20.dp)
                ) {

                    Text(
                        text =
                            "Resumen de la unidad",

                        color =
                            Color.White,

                        fontSize =
                            13.sp
                    )


                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )


                    Text(
                        text =
                            "${uiState.rendimientoGeneral}% rendimiento",

                        color =
                            Color.White,

                        fontSize =
                            30.sp,

                        fontWeight =
                            FontWeight.Bold
                    )


                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )


                    LinearProgressIndicator(
                        progress = {

                            uiState
                                .rendimientoGeneral /
                                    100f
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        color =
                            Paleta.Ambar,

                        trackColor =
                            Color.White.copy(
                                alpha = 0.25f
                            )
                    )


                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )


                    Text(
                        text =
                            "${uiState.evaluacionesCompletadas} de 3 evaluaciones completadas",

                        color =
                            Color.White.copy(
                                alpha = 0.85f
                            ),

                        fontSize =
                            12.sp
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )


            // -------------------------
            // ESTADO DEL INFORME
            // -------------------------

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults
                        .cardColors(

                            containerColor =

                                if (
                                    uiState.estado ==
                                    EstadoInformePedagogico
                                        .VALIDADO
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
                        Modifier.padding(14.dp),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =

                            if (
                                uiState.estado ==
                                EstadoInformePedagogico
                                    .VALIDADO
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
                                uiState.estado ==
                                EstadoInformePedagogico
                                    .VALIDADO
                            ) {

                                Paleta.VerdeOscuro

                            } else {

                                Paleta.AmbarOscuro
                            }
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

                            if (
                                uiState.estado ==
                                EstadoInformePedagogico
                                    .VALIDADO
                            ) {

                                Paleta.VerdeOscuro

                            } else {

                                Paleta.AmbarOscuro
                            }
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )


            // -------------------------
            // RENDIMIENTO POR OBJETIVO
            // -------------------------

            Text(
                text =
                    "Rendimiento por etapa",

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    16.sp,

                color =
                    Paleta.Texto
            )


            Text(
                text =
                    "Resultados asociados a ${unidad.oa}",

                fontSize =
                    12.sp,

                color =
                    Paleta.TextoSuave
            )


            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )


            uiState.rendimientos
                .forEach {
                        rendimiento ->

                    RendimientoItem(
                        nombre =
                            rendimiento.nombre,

                        porcentaje =
                            rendimiento.porcentaje
                    )


                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )
                }


            // -------------------------
            // FORTALEZAS
            // -------------------------

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            Text(
                text =
                    "Fortalezas",

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    16.sp,

                color =
                    Paleta.Texto
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults
                        .cardColors(
                            containerColor =
                                Paleta.VerdeClaro
                        ),

                shape =
                    RoundedCornerShape(
                        14.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(16.dp)
                ) {

                    if (
                        uiState
                            .fortalezas
                            .isEmpty()
                    ) {

                        Text(
                            text =
                                if (
                                    uiState
                                        .evaluacionesCompletadas ==
                                    0
                                ) {

                                    "Aún no existen resultados para identificar fortalezas."

                                } else {

                                    "Todavía no se identifican contenidos con rendimiento igual o superior al 70%."
                                },

                            fontSize =
                                13.sp,

                            color =
                                Paleta.VerdeOscuro
                        )

                    } else {

                        uiState
                            .fortalezas
                            .forEach {
                                    fortaleza ->

                                Row(
                                    verticalAlignment =
                                        Alignment.CenterVertically,

                                    modifier =
                                        Modifier.padding(
                                            vertical = 4.dp
                                        )
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
                                        modifier =
                                            Modifier.width(
                                                8.dp
                                            )
                                    )


                                    Text(
                                        text =
                                            fortaleza,

                                        fontSize =
                                            13.sp,

                                        color =
                                            Paleta.VerdeOscuro
                                    )
                                }
                            }
                    }
                }
            }


            // -------------------------
            // POR REFORZAR
            // -------------------------

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )


            Text(
                text =
                    "Contenidos por reforzar",

                fontWeight =
                    FontWeight.Bold,

                fontSize =
                    16.sp,

                color =
                    Paleta.Texto
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
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
                        14.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(16.dp)
                ) {

                    if (
                        uiState
                            .porReforzar
                            .isEmpty()
                    ) {

                        Text(
                            text =
                                if (
                                    uiState
                                        .evaluacionesCompletadas ==
                                    0
                                ) {

                                    "Aún no existen resultados para detectar dificultades."

                                } else {

                                    "No se detectaron contenidos críticos bajo el 70%."
                                },

                            fontSize =
                                13.sp,

                            color =
                                Paleta.AmbarOscuro
                        )

                    } else {

                        uiState
                            .porReforzar
                            .forEach {
                                    contenido ->

                                Row(
                                    verticalAlignment =
                                        Alignment.CenterVertically,

                                    modifier =
                                        Modifier.padding(
                                            vertical = 4.dp
                                        )
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Warning,

                                        contentDescription =
                                            null,

                                        tint =
                                            Paleta.AmbarOscuro
                                    )


                                    Spacer(
                                        modifier =
                                            Modifier.width(
                                                8.dp
                                            )
                                    )


                                    Text(
                                        text =
                                            contenido,

                                        fontSize =
                                            13.sp,

                                        color =
                                            Paleta.AmbarOscuro
                                    )
                                }
                            }
                    }
                }
            }


            uiState.mensaje
                ?.let { mensaje ->

                    Spacer(
                        modifier =
                            Modifier.height(
                                16.dp
                            )
                    )


                    Text(
                        text =
                            mensaje,

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Medium,

                        color =

                            if (
                                uiState.estado ==
                                EstadoInformePedagogico
                                    .VALIDADO
                            ) {

                                Paleta.VerdeOscuro

                            } else {

                                MaterialTheme
                                    .colorScheme
                                    .error
                            }
                    )
                }


            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )


            // -------------------------
            // VALIDAR
            // -------------------------

            Button(
                onClick = {

                    informeViewModel
                        .validarInforme()
                },

                enabled =
                    uiState.estado !=
                            EstadoInformePedagogico
                                .VALIDADO,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),

                colors =
                    ButtonDefaults
                        .buttonColors(
                            containerColor =
                                Paleta.Azul,

                            disabledContainerColor =
                                Paleta.Verde
                        ),

                shape =
                    RoundedCornerShape(
                        12.dp
                    )
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

                        if (
                            uiState.estado ==
                            EstadoInformePedagogico
                                .VALIDADO
                        ) {

                            "Informe validado"

                        } else {

                            "Validar informe"
                        },

                    fontWeight =
                        FontWeight.Bold
                )
            }


            Spacer(modifier = Modifier.height(12.dp))
            BotonDescargarInforme(uiState, asignatura, unidad)
            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )
        }
    }
}


@Composable
private fun RendimientoItem(
    nombre: String,
    porcentaje: Int
) {

    Column(
        modifier =
            Modifier.fillMaxWidth()
    ) {

        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Text(
                text =
                    nombre,

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.Medium,

                color =
                    Paleta.Texto
            )


            Text(
                text =
                    "$porcentaje%",

                fontSize =
                    14.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Paleta.Texto
            )
        }


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        LinearProgressIndicator(
            progress = {
                porcentaje / 100f
            },

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(7.dp),

            color =

                when {

                    porcentaje >= 70 ->
                        Paleta.Verde

                    porcentaje >= 50 ->
                        Paleta.Ambar

                    else ->
                        MaterialTheme
                            .colorScheme
                            .error
                },

            trackColor =
                Paleta.Borde
        )
    }
}

@Composable
private fun FichaInforme(uiState: InformePedagogicoUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            FilaFicha("Estudiante", "${uiState.estudiante} · ${uiState.curso}")
            FilaFicha("Docente responsable", uiState.docenteResponsable)
            FilaFicha("Fecha de generación", uiState.fechaGeneracion)
            FilaFicha("Respuestas correctas", "${uiState.respuestasCorrectas}")
            FilaFicha("Respuestas incorrectas", "${uiState.respuestasIncorrectas}")
        }
    }
}

@Composable
private fun FilaFicha(nombre: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(nombre, fontSize = 13.sp, color = Paleta.TextoSuave)
        Spacer(modifier = Modifier.width(12.dp))
        Text(valor, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Paleta.Texto)
    }
}