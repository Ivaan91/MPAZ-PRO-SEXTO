package com.example.mpaz_pro_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mpaz_pro_6.data.AppDatabase
import com.example.mpaz_pro_6.data.UsuarioDao
import com.example.mpaz_pro_6.model.Etapa
import com.example.mpaz_pro_6.ui.screens.*
import com.example.mpaz_pro_6.ui.theme.MPAZPRO6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getDatabase(this)
        val usuarioDao = db.usuarioDao()

        // Íconos de las barras del sistema siempre oscuros (la app usa fondos claros).
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            MPAZPRO6Theme {
                AppNavigation(usuarioDao)
            }
        }
    }
}

@Composable
fun AppNavigation(usuarioDao: UsuarioDao) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { PantallaLogin(navController, usuarioDao) }
        composable("home_estudiante") { PantallaHomeEstudiante(navController) }

        composable(
            route = "detalle_unidad/{asignaturaId}",
            arguments = listOf(navArgument("asignaturaId") { type = NavType.StringType })
        ) { entrada ->
            PantallaDetalleUnidad(navController, entrada.arguments?.getString("asignaturaId") ?: "3")
        }

        composable(
            route = "contenido/{asignaturaId}/{indice}",
            arguments = listOf(
                navArgument("asignaturaId") { type = NavType.StringType },
                navArgument("indice") { type = NavType.IntType }
            )
        ) { entrada ->
            PantallaContenido(
                navController,
                entrada.arguments?.getString("asignaturaId") ?: "3",
                entrada.arguments?.getInt("indice") ?: 0
            )
        }

        composable(
            route = "quiz/{asignaturaId}/{etapa}",
            arguments = listOf(
                navArgument("asignaturaId") { type = NavType.StringType },
                navArgument("etapa") { type = NavType.StringType }
            )
        ) { entrada ->
            PantallaQuiz(
                navController,
                entrada.arguments?.getString("asignaturaId") ?: "3",
                Etapa.desdeRuta(entrada.arguments?.getString("etapa") ?: "")
            )
        }

        composable(
            route = "resultado/{asignaturaId}/{etapa}",
            arguments = listOf(
                navArgument("asignaturaId") { type = NavType.StringType },
                navArgument("etapa") { type = NavType.StringType }
            )
        ) { entrada ->
            PantallaResultadoPrueba(
                navController,
                entrada.arguments?.getString("asignaturaId") ?: "3",
                Etapa.desdeRuta(entrada.arguments?.getString("etapa") ?: "")
            )
        }

        composable("home_docente") { PantallaHomeDocente(navController) }
        composable("informe_pedagogico") { PantallaInformePedagogico(navController) }
        composable("home_utp") { PantallaHomeUTP(navController) }
    }
}