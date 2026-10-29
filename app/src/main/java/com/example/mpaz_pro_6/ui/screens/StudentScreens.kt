package com.example.mpaz_pro_6.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.ui.theme.Paleta
import com.example.mpaz_pro_6.util.NotificationHelper


@Composable
fun PantallaHomeEstudiante(
    navController: NavHostController
) {

    val context =
        LocalContext.current


    val avanceGeneral =
        ProgresoRepository.avanceGeneral()


    /*
     * Buscamos la asignatura con menor avance.
     *
     * Así la notificación no muestra
     * un texto cualquiera, sino una sugerencia
     * relacionada con el progreso real.
     */
    val asignaturaSugerida =
        MockRepository.asignaturas
            .minByOrNull {

                ProgresoRepository
                    .avance(it.id)
            }
            ?: MockRepository.asignaturas.first()


    var mensajeNotificacion by remember {
        mutableStateOf<String?>(null)
    }


    /*
     * Android 13+ pide permiso
     * para enviar notificaciones.
     */
    val permisoNotificaciones =
        rememberLauncherForActivityResult(

            contract =
                ActivityResultContracts
                    .RequestPermission()

        ) { concedido ->

            if (concedido) {

                val enviada =
                    NotificationHelper
                        .mostrarRecordatorio(
                            context,
                            asignaturaSugerida.nombre
                        )


                mensajeNotificacion =

                    if (enviada) {

                        "Recordatorio enviado."

                    } else {

                        "No se pudo enviar el recordatorio."
                    }

            } else {

                mensajeNotificacion =
                    "No se concedió el permiso de notificaciones."
            }
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
        // PERFIL
        // -------------------------

        Row(
            verticalAlignment =
                Alignment.CenterVertically,

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(22.dp)
                        )
                        .border(
                            1.dp,
                            Color.LightGray,
                            RoundedCornerShape(22.dp)
                        ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text = "SC",
                        color =
                            Paleta.AzulOscuro,
                        fontWeight =
                            FontWeight.Bold
                    )
                }


                Spacer(
                    modifier =
                        Modifier.width(12.dp)
                )


                Column {

                    Text(
                        text =
                            "Lunes 5 de octubre",

                        fontSize = 11.sp,

                        color =
                            Paleta.TextoSuave
                    )


                    Text(
                        text =
                            "¡Hola, Sofía!",

                        fontSize = 22.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Paleta.Texto
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // -------------------------
        // AVANCE GENERAL
        // -------------------------

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
                    Modifier.padding(18.dp)
            ) {

                Text(
                    text =
                        "Tu avance general",

                    color =
                        Color.White,

                    fontSize = 13.sp
                )


                Text(
                    text =
                        "${(avanceGeneral * 100).toInt()}%",

                    color =
                        Color.White,

                    fontSize = 34.sp,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )


                LinearProgressIndicator(
                    progress = {
                        avanceGeneral
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    color =
                        Paleta.Ambar,

                    trackColor =
                        Color.White.copy(
                            alpha = 0.3f
                        )
                )


                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )


                Text(
                    text =
                        if (
                            avanceGeneral == 0f
                        ) {

                            "Empieza por la asignatura que prefieras."

                        } else {

                            "Vas muy bien. ¡Sigue avanzando!"
                        },

                    color =
                        Color.White,

                    fontSize = 12.sp
                )
            }
        }


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // -------------------------
        // RECORDATORIO NATIVO
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
                RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier =
                    Modifier.padding(16.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Notifications,

                        contentDescription =
                            null,

                        tint =
                            Paleta.Azul
                    )


                    Spacer(
                        modifier =
                            Modifier.width(10.dp)
                    )


                    Column {

                        Text(
                            text =
                                "Recordatorio de estudio",

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Paleta.AzulOscuro
                        )


                        Text(
                            text =
                                "Puedes recibir un recordatorio para continuar tu avance.",

                            fontSize = 12.sp,

                            color =
                                Paleta.TextoSuave
                        )
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )


                Button(
                    onClick = {

                        /*
                         * Android 13 o superior:
                         * comprobamos el permiso.
                         */
                        if (
                            Build.VERSION.SDK_INT >=
                            Build.VERSION_CODES.TIRAMISU
                        ) {

                            val permiso =
                                ContextCompat
                                    .checkSelfPermission(
                                        context,
                                        Manifest.permission
                                            .POST_NOTIFICATIONS
                                    )


                            if (
                                permiso ==
                                PackageManager
                                    .PERMISSION_GRANTED
                            ) {

                                val enviada =
                                    NotificationHelper
                                        .mostrarRecordatorio(
                                            context,
                                            asignaturaSugerida.nombre
                                        )


                                mensajeNotificacion =

                                    if (enviada) {

                                        "Recordatorio enviado."

                                    } else {

                                        "No se pudo enviar el recordatorio."
                                    }

                            } else {

                                /*
                                 * Aparecerá la ventana
                                 * nativa de Android:
                                 *
                                 * ¿Permitir notificaciones?
                                 */
                                permisoNotificaciones.launch(
                                    Manifest.permission
                                        .POST_NOTIFICATIONS
                                )
                            }

                        } else {

                            /*
                             * Android 12 o inferior
                             * no necesita este permiso
                             * en tiempo de ejecución.
                             */
                            val enviada =
                                NotificationHelper
                                    .mostrarRecordatorio(
                                        context,
                                        asignaturaSugerida.nombre
                                    )


                            mensajeNotificacion =

                                if (enviada) {

                                    "Recordatorio enviado."

                                } else {

                                    "No se pudo enviar el recordatorio."
                                }
                        }
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

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
                            Icons.Default.Notifications,

                        contentDescription =
                            null
                    )


                    Spacer(
                        modifier =
                            Modifier.width(8.dp)
                    )


                    Text(
                        text =
                            "Recordarme estudiar"
                    )
                }


                mensajeNotificacion
                    ?.let { mensaje ->

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )


                        Text(
                            text = mensaje,

                            fontSize = 12.sp,

                            color =
                                Paleta.TextoSuave
                        )
                    }
            }
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // -------------------------
        // ASIGNATURAS
        // -------------------------

        Text(
            text = "Tus asignaturas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Paleta.Texto
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        MockRepository
            .asignaturas
            .forEach { item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 5.dp
                        )
                        .clickable {

                            navController.navigate(
                                "detalle_unidad/${item.id}"
                            )
                        },

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.White
                        ),

                    shape =
                        RoundedCornerShape(12.dp)
                ) {

                    Row(
                        modifier =
                            Modifier.padding(
                                16.dp
                            ),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Column(
                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Text(
                                text =
                                    item.nombre,

                                fontWeight =
                                    FontWeight.Bold,

                                fontSize =
                                    14.sp,

                                color =
                                    Paleta.Texto
                            )


                            Text(
                                text =
                                    item.unidad,

                                fontSize =
                                    12.sp,

                                color =
                                    Paleta.TextoSuave
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(6.dp)
                            )


                            LinearProgressIndicator(
                                progress = {

                                    ProgresoRepository
                                        .avance(
                                            item.id
                                        )
                                },

                                modifier =
                                    Modifier
                                        .fillMaxWidth(
                                            0.6f
                                        ),

                                color =
                                    Color(
                                        item.colorHex
                                    ),

                                trackColor =
                                    Paleta.Borde
                            )
                        }


                        Icon(
                            imageVector =
                                Icons.Default.ChevronRight,

                            contentDescription =
                                null,

                            tint =
                                Paleta.TextoSuave
                        )
                    }
                }
            }
    }
}