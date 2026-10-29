package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.ui.theme.Paleta


@Composable
fun PantallaHomeDocente(
    navController: NavHostController
) {

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

        // PERFIL DOCENTE
        Row(
            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically,

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically,

                modifier =
                    Modifier.weight(1f)
            ) {

                Box(
                    modifier =
                        Modifier
                            .size(44.dp)
                            .background(
                                Paleta.AmbarClaro,
                                RoundedCornerShape(
                                    22.dp
                                )
                            ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text =
                            "JM",

                        color =
                            Paleta.AmbarOscuro,

                        fontWeight =
                            FontWeight.Bold
                    )
                }


                Spacer(
                    modifier =
                        Modifier.width(
                            12.dp
                        )
                )


                Column {

                    Text(
                        text =
                            "Vista docente",

                        fontSize =
                            11.sp,

                        color =
                            Paleta.TextoSuave
                    )


                    Text(
                        text =
                            "Hola, Javier",

                        fontSize =
                            22.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Paleta.Texto
                    )
                }
            }


            BotonCerrarSesion(
                navController =
                    navController,

                rutaHome =
                    "home_docente"
            )
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // AVANCE GENERAL
        Card(
            modifier =
                Modifier.fillMaxWidth(),

            colors =
                CardDefaults.cardColors(
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
                        "Avance promedio del curso",
                    color = Color.White,
                    fontSize = 13.sp
                )

                Text(
                    text = "67%",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )


                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "24 activos",
                        color = Color.White,
                        fontSize = 12.sp
                    )

                    Text(
                        text = "18 al día",
                        color = Color.White,
                        fontSize = 12.sp
                    )

                    Text(
                        text = "4 pendientes",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        Text(
            text = "Gestión académica",
            fontSize = 16.sp,
            fontWeight =
                FontWeight.Bold,
            color = Paleta.Texto
        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        // CONFIGURACIÓN DE UNIDAD
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                    navController.navigate(
                        "configuracion_unidad"
                    )
                },

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        Paleta.AzulClaro
                ),

            shape =
                RoundedCornerShape(16.dp)
        ) {

            Row(
                modifier =
                    Modifier.padding(16.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(
                                12.dp
                            )
                        ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Settings,
                        contentDescription =
                            null,
                        tint = Paleta.Azul
                    )
                }


                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )


                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(
                        text =
                            "Configurar unidad",
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Paleta.AzulOscuro,
                        fontSize = 15.sp
                    )

                    Text(
                        text =
                            "Revisa la unidad y configura los intentos de la prueba.",
                        fontSize = 12.sp,
                        color =
                            Paleta.TextoSuave
                    )
                }


                Icon(
                    imageVector =
                        Icons.Default.ChevronRight,
                    contentDescription =
                        "Abrir configuración",
                    tint = Paleta.Azul
                )
            }
        }


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // INFORMES
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {

                    navController.navigate(
                        "informe_pedagogico"
                    )
                },

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        Paleta.AmbarClaro
                ),

            shape =
                RoundedCornerShape(16.dp)
        ) {

            Row(
                modifier =
                    Modifier.padding(16.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(
                                12.dp
                            )
                        ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.MenuBook,
                        contentDescription =
                            null,
                        tint =
                            Paleta.AmbarOscuro
                    )
                }


                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )


                Column(
                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(
                        text =
                            "3 informes pendientes de validar",
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Paleta.AmbarOscuro,
                        fontSize = 14.sp
                    )

                    Text(
                        text =
                            "Revisa los resultados antes de compartirlos.",
                        fontSize = 12.sp,
                        color =
                            Paleta.TextoSuave
                    )
                }


                Icon(
                    imageVector =
                        Icons.Default.ChevronRight,
                    contentDescription =
                        "Abrir informes",
                    tint =
                        Paleta.AmbarOscuro
                )
            }
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }
}