package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.ui.theme.Paleta


@Composable
fun BotonCerrarSesion(
    navController: NavHostController,
    rutaHome: String
) {

    TextButton(
        onClick = {

            navController.navigate(
                "login"
            ) {

                /*
                 * Eliminamos la pantalla principal
                 * del usuario anterior del historial.
                 *
                 * Así no se puede volver a la sesión
                 * anterior usando el botón Atrás.
                 */
                popUpTo(
                    rutaHome
                ) {

                    inclusive =
                        true
                }


                launchSingleTop =
                    true
            }
        },

        shape =
            RoundedCornerShape(
                10.dp
            )
    ) {

        Text(
            text =
                "Cerrar sesión",

            fontWeight =
                FontWeight.Medium,

            color =
                Paleta.Azul
        )
    }
}