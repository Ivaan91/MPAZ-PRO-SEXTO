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
import androidx.compose.material.icons.filled.Lock
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
import com.example.mpaz_pro_6.model.GestionAcademicaRepository
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.ui.theme.Paleta
import com.example.mpaz_pro_6.util.NotificationHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun PantallaHomeEstudiante(
    navController:
    NavHostController
) {

    val context =
        LocalContext.current


    val asignaturasDisponibles =
        MockRepository
            .asignaturas
            .filter {

                GestionAcademicaRepository
                    .unidadDisponibleEstudiante(
                        it.id
                    )
            }


    val avanceGeneral =

        if (
            asignaturasDisponibles
                .isEmpty()
        ) {

            0f

        } else {

            asignaturasDisponibles
                .map {

                    ProgresoRepository
                        .avance(
                            it.id
                        )
                }
                .average()
                .toFloat()
        }


    val asignaturaSugerida =
        asignaturasDisponibles
            .minByOrNull {

                ProgresoRepository
                    .avance(
                        it.id
                    )
            }


    val fechaActual =
        remember {

            SimpleDateFormat(
                "EEEE d 'de' MMMM",
                Locale(
                    "es",
                    "CL"
                )
            )
                .format(
                    Date()
                )
                .replaceFirstChar {

                    if (
                        it.isLowerCase()
                    ) {

                        it.titlecase()

                    } else {

                        it.toString()
                    }
                }
        }


    var mensajeNotificacion
            by remember {

                mutableStateOf<String?>(
                    null
                )
            }


    val permisoNotificaciones =
        rememberLauncherForActivityResult(

            contract =
                ActivityResultContracts
                    .RequestPermission()

        ) { concedido ->

            val sugerida =
                asignaturaSugerida


            if (
                concedido &&
                sugerida != null
            ) {

                val enviada =
                    NotificationHelper
                        .mostrarRecordatorio(

                            context,

                            sugerida.nombre
                        )


                mensajeNotificacion =

                    if (enviada) {

                        "Recordatorio enviado."

                    } else {

                        "No se pudo enviar el recordatorio."
                    }

            } else if (
                !concedido
            ) {

                mensajeNotificacion =
                    "No se concedió el permiso de notificaciones."
            }
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
        // PERFIL
        // -------------------------

        Row(
            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier =
                    Modifier
                        .size(44.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(
                                22.dp
                            )
                        )
                        .border(
                            1.dp,
                            Color.LightGray,
                            RoundedCornerShape(
                                22.dp
                            )
                        ),

                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text =
                        "SC",

                    color =
                        Paleta.AzulOscuro,

                    fontWeight =
                        FontWeight.Bold
                )
            }


            Spacer(
                Modifier.width(
                    12.dp
                )
            )


            Column {

                Text(
                    text =
                        fechaActual,

                    fontSize =
                        11.sp,

                    color =
                        Paleta.TextoSuave
                )


                Text(
                    text =
                        "¡Hola, Sofía!",

                    fontSize =
                        22.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Paleta.Texto
                )
            }
        }


        Spacer(
            Modifier.height(
                20.dp
            )
        )


        // -------------------------
        // PROGRESO
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
                        18.dp
                    )
            ) {

                Text(
                    text =
                        "Tu avance general",

                    color =
                        Color.White,

                    fontSize =
                        13.sp
                )


                Text(
                    text =
                        "${(avanceGeneral * 100).toInt()}%",

                    color =
                        Color.White,

                    fontSize =
                        34.sp,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    Modifier.height(
                        6.dp
                    )
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
                    Modifier.height(
                        10.dp
                    )
                )


                Text(
                    text =

                        if (
                            asignaturasDisponibles
                                .isEmpty()
                        ) {

                            "Aún no tienes unidades autorizadas por UTP."

                        } else if (
                            avanceGeneral ==
                            0f
                        ) {

                            "Ya tienes contenido disponible para comenzar."

                        } else {

                            "Vas muy bien. ¡Sigue avanzando!"
                        },

                    color =
                        Color.White,

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
        // NOTIFICACIÓN
        // -------------------------

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
                    16.dp
                )
        ) {

            Column(
                modifier =
                    Modifier.padding(
                        16.dp
                    )
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =
                            Icons.Default
                                .Notifications,

                        contentDescription =
                            null,

                        tint =
                            Paleta.Azul
                    )


                    Spacer(
                        Modifier.width(
                            10.dp
                        )
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

                                if (
                                    asignaturaSugerida !=
                                    null
                                ) {

                                    "Recibe un recordatorio para continuar ${asignaturaSugerida.nombre}."

                                } else {

                                    "Disponible cuando UTP autorice una unidad."
                                },

                            fontSize =
                                12.sp,

                            color =
                                Paleta.TextoSuave
                        )
                    }
                }


                Spacer(
                    Modifier.height(
                        12.dp
                    )
                )


                Button(
                    enabled =
                        asignaturaSugerida !=
                                null,

                    onClick = {

                        val sugerida =
                            asignaturaSugerida
                                ?: return@Button


                        if (
                            Build.VERSION.SDK_INT >=
                            Build.VERSION_CODES
                                .TIRAMISU
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

                                            sugerida.nombre
                                        )


                                mensajeNotificacion =

                                    if (enviada) {

                                        "Recordatorio enviado."

                                    } else {

                                        "No se pudo enviar el recordatorio."
                                    }

                            } else {

                                permisoNotificaciones
                                    .launch(

                                        Manifest.permission
                                            .POST_NOTIFICATIONS
                                    )
                            }

                        } else {

                            val enviada =
                                NotificationHelper
                                    .mostrarRecordatorio(

                                        context,

                                        sugerida.nombre
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
                        RoundedCornerShape(
                            12.dp
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default
                                .Notifications,

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
                            "Recordarme estudiar"
                    )
                }


                mensajeNotificacion
                    ?.let { mensaje ->

                        Spacer(
                            Modifier.height(
                                8.dp
                            )
                        )


                        Text(
                            text =
                                mensaje,

                            fontSize =
                                12.sp,

                            color =
                                Paleta.TextoSuave
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
        // ASIGNATURAS
        // -------------------------

        Text(
            text =
                "Tus asignaturas",

            fontSize =
                18.sp,

            fontWeight =
                FontWeight.Bold,

            color =
                Paleta.Texto
        )


        Spacer(
            Modifier.height(
                8.dp
            )
        )


        MockRepository
            .asignaturas
            .forEach { item ->

                val disponible =
                    GestionAcademicaRepository
                        .unidadDisponibleEstudiante(
                            item.id
                        )


                val motivoBloqueo =
                    GestionAcademicaRepository
                        .motivoBloqueoEstudiante(
                            item.id
                        )


                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical =
                                    5.dp
                            )
                            .clickable(
                                enabled =
                                    disponible
                            ) {

                                navController
                                    .navigate(

                                        "detalle_unidad/${item.id}"
                                    )
                            },

                    colors =
                        CardDefaults
                            .cardColors(

                                containerColor =

                                    if (
                                        disponible
                                    ) {

                                        Color.White

                                    } else {

                                        Paleta.Bloqueado
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
                                    item.nombre,

                                fontWeight =
                                    FontWeight.Bold,

                                fontSize =
                                    14.sp,

                                color =

                                    if (
                                        disponible
                                    ) {

                                        Paleta.Texto

                                    } else {

                                        Paleta.TextoSuave
                                    }
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
                                Modifier.height(
                                    5.dp
                                )
                            )


                            if (
                                disponible
                            ) {

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
                                                0.65f
                                            ),

                                    color =
                                        Color(
                                            item.colorHex
                                        ),

                                    trackColor =
                                        Paleta.Borde
                                )

                            } else {

                                Text(
                                    text =
                                        motivoBloqueo
                                            ?: "Unidad no disponible",

                                    fontSize =
                                        11.sp,

                                    fontWeight =
                                        FontWeight.Medium,

                                    color =
                                        Paleta.TextoSuave
                                )
                            }
                        }


                        Icon(
                            imageVector =

                                if (
                                    disponible
                                ) {

                                    Icons.Default
                                        .ChevronRight

                                } else {

                                    Icons.Default
                                        .Lock
                                },

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