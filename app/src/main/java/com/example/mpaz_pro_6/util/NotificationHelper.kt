package com.example.mpaz_pro_6.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.mpaz_pro_6.MainActivity
import com.example.mpaz_pro_6.R


object NotificationHelper {

    private const val CANAL_ID =
        "recordatorios_estudio"

    private const val NOTIFICACION_ID =
        1001


    fun crearCanal(
        context: Context
    ) {

        // Los canales existen desde Android 8.
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val canal =
                NotificationChannel(
                    CANAL_ID,
                    "Recordatorios de estudio",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {

                    description =
                        "Recordatorios para continuar el avance académico."
                }


            val notificationManager =
                context.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager


            notificationManager
                .createNotificationChannel(
                    canal
                )
        }
    }


    fun mostrarRecordatorio(
        context: Context,
        asignatura: String
    ): Boolean {

        // En Android 13 o superior comprobamos
        // que exista el permiso.
        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            val permiso =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                )


            if (
                permiso !=
                PackageManager.PERMISSION_GRANTED
            ) {

                return false
            }
        }


        crearCanal(context)


        // Al tocar la notificación
        // vuelve a abrir la aplicación.
        val intent =
            Intent(
                context,
                MainActivity::class.java
            ).apply {

                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            }


        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )


        val texto =
            "Continúa tu avance en $asignatura cuando tengas un momento."


        val notificacion =
            NotificationCompat.Builder(
                context,
                CANAL_ID
            )
                .setSmallIcon(
                    R.drawable.ic_launcher_foreground
                )
                .setContentTitle(
                    "MPAZ PRO 6.º"
                )
                .setContentText(
                    texto
                )
                .setStyle(
                    NotificationCompat
                        .BigTextStyle()
                        .bigText(texto)
                )
                .setPriority(
                    NotificationCompat.PRIORITY_DEFAULT
                )
                .setContentIntent(
                    pendingIntent
                )
                .setAutoCancel(true)
                .build()


        NotificationManagerCompat
            .from(context)
            .notify(
                NOTIFICACION_ID,
                notificacion
            )


        return true
    }
}