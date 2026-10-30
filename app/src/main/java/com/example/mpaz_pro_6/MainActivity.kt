package com.example.mpaz_pro_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mpaz_pro_6.data.AppDatabase
import com.example.mpaz_pro_6.data.UsuarioDao
import com.example.mpaz_pro_6.model.Etapa
import com.example.mpaz_pro_6.model.GestionAcademicaRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.ui.screens.*
import com.example.mpaz_pro_6.ui.theme.MPAZPRO6Theme


class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )


        // -------------------------
        // BASE DE DATOS ROOM
        // -------------------------

        val db =
            AppDatabase
                .getDatabase(this)


        val usuarioDao =
            db.usuarioDao()


        // -------------------------
        // PERSISTENCIA LOCAL
        // -------------------------

        /*
         * Cargamos el progreso,
         * intentos y configuración
         * docente guardados.
         */
        ProgresoRepository
            .inicializar(this)


        /*
         * Cargamos autorizaciones
         * UTP y estados de informes.
         */
        GestionAcademicaRepository
            .inicializar(this)


        // -------------------------
        // BARRAS DEL SISTEMA
        // -------------------------

        enableEdgeToEdge(

            statusBarStyle =
                SystemBarStyle.light(

                    android.graphics
                        .Color.TRANSPARENT,

                    android.graphics
                        .Color.TRANSPARENT
                ),

            navigationBarStyle =
                SystemBarStyle.light(

                    android.graphics
                        .Color.TRANSPARENT,

                    android.graphics
                        .Color.TRANSPARENT
                )
        )


        // -------------------------
        // INTERFAZ
        // -------------------------

        setContent {

            MPAZPRO6Theme {

                AppNavigation(
                    usuarioDao
                )
            }
        }
    }
}


@Composable
fun AppNavigation(
    usuarioDao: UsuarioDao
) {

    val navController =
        rememberNavController()


    NavHost(

        navController =
            navController,

        startDestination =
            "login",


        // -------------------------
        // AVANZAR
        // -------------------------

        enterTransition = {

            fadeIn(
                animationSpec =
                    tween(
                        durationMillis =
                            250
                    )
            ) +

                    slideIntoContainer(

                        towards =
                            AnimatedContentTransitionScope
                                .SlideDirection
                                .Start,

                        animationSpec =
                            tween(
                                durationMillis =
                                    300
                            )
                    )
        },


        exitTransition = {

            fadeOut(
                animationSpec =
                    tween(
                        durationMillis =
                            200
                    )
            ) +

                    slideOutOfContainer(

                        towards =
                            AnimatedContentTransitionScope
                                .SlideDirection
                                .Start,

                        animationSpec =
                            tween(
                                durationMillis =
                                    300
                            )
                    )
        },


        // -------------------------
        // VOLVER
        // -------------------------

        popEnterTransition = {

            fadeIn(
                animationSpec =
                    tween(
                        durationMillis =
                            250
                    )
            ) +

                    slideIntoContainer(

                        towards =
                            AnimatedContentTransitionScope
                                .SlideDirection
                                .End,

                        animationSpec =
                            tween(
                                durationMillis =
                                    300
                            )
                    )
        },


        popExitTransition = {

            fadeOut(
                animationSpec =
                    tween(
                        durationMillis =
                            200
                    )
            ) +

                    slideOutOfContainer(

                        towards =
                            AnimatedContentTransitionScope
                                .SlideDirection
                                .End,

                        animationSpec =
                            tween(
                                durationMillis =
                                    300
                            )
                    )
        }

    ) {


        // =========================
        // LOGIN
        // =========================

        composable(
            route =
                "login"
        ) {

            PantallaLogin(
                navController,
                usuarioDao
            )
        }


        // =========================
        // ESTUDIANTE
        // =========================

        composable(
            route =
                "home_estudiante"
        ) {

            PantallaHomeEstudiante(
                navController
            )
        }


        composable(

            route =
                "detalle_unidad/{asignaturaId}",

            arguments =
                listOf(

                    navArgument(
                        "asignaturaId"
                    ) {

                        type =
                            NavType.StringType
                    }
                )

        ) { entrada ->


            PantallaDetalleUnidad(

                navController,

                entrada.arguments
                    ?.getString(
                        "asignaturaId"
                    )
                    ?: "3"
            )
        }


        composable(

            route =
                "contenido/{asignaturaId}/{indice}",

            arguments =
                listOf(

                    navArgument(
                        "asignaturaId"
                    ) {

                        type =
                            NavType.StringType
                    },


                    navArgument(
                        "indice"
                    ) {

                        type =
                            NavType.IntType
                    }
                )

        ) { entrada ->


            PantallaContenido(

                navController,

                entrada.arguments
                    ?.getString(
                        "asignaturaId"
                    )
                    ?: "3",

                entrada.arguments
                    ?.getInt(
                        "indice"
                    )
                    ?: 0
            )
        }


        composable(

            route =
                "quiz/{asignaturaId}/{etapa}",

            arguments =
                listOf(

                    navArgument(
                        "asignaturaId"
                    ) {

                        type =
                            NavType.StringType
                    },


                    navArgument(
                        "etapa"
                    ) {

                        type =
                            NavType.StringType
                    }
                )

        ) { entrada ->


            PantallaQuiz(

                navController,

                entrada.arguments
                    ?.getString(
                        "asignaturaId"
                    )
                    ?: "3",

                Etapa.desdeRuta(

                    entrada.arguments
                        ?.getString(
                            "etapa"
                        )
                        ?: ""
                )
            )
        }


        composable(

            route =
                "resultado/{asignaturaId}/{etapa}",

            arguments =
                listOf(

                    navArgument(
                        "asignaturaId"
                    ) {

                        type =
                            NavType.StringType
                    },


                    navArgument(
                        "etapa"
                    ) {

                        type =
                            NavType.StringType
                    }
                )

        ) { entrada ->


            PantallaResultadoPrueba(

                navController,

                entrada.arguments
                    ?.getString(
                        "asignaturaId"
                    )
                    ?: "3",

                Etapa.desdeRuta(

                    entrada.arguments
                        ?.getString(
                            "etapa"
                        )
                        ?: ""
                )
            )
        }


        // =========================
        // DOCENTE
        // =========================

        composable(
            route =
                "home_docente"
        ) {

            PantallaHomeDocente(
                navController
            )
        }


        composable(
            route =
                "configuracion_unidad"
        ) {

            PantallaConfiguracionUnidad(
                navController
            )
        }


        composable(
            route = "lista_informes"
        ) {
            PantallaListaInformes(navController)
        }

        composable(
            route = "informe_pedagogico/{asignaturaId}",
            arguments = listOf(
                navArgument("asignaturaId") {
                    type = NavType.StringType
                }
            )
        ) {
            PantallaInformePedagogico(navController)
        }


        // =========================
        // UTP
        // =========================

        composable(
            route =
                "home_utp"
        ) {

            PantallaHomeUTP(
                navController
            )
        }
    }
}