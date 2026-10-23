package com.example.mpaz_pro_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mpaz_pro_6.data.AppDatabase
import com.example.mpaz_pro_6.ui.screens.*
import com.example.mpaz_pro_6.ui.theme.MPAZPRO6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getDatabase(this)
        val usuarioDao = db.usuarioDao()

        enableEdgeToEdge()
        setContent {
            MPAZPRO6Theme {
                AppNavigation(usuarioDao)
            }
        }
    }
}

@Composable
fun AppNavigation(usuarioDao: com.example.mpaz_pro_6.data.UsuarioDao) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { PantallaLogin(navController, usuarioDao) }
        composable("home_estudiante") { PantallaHomeEstudiante(navController) }
        composable("detalle_unidad") { PantallaDetalleUnidad(navController) }
        composable("quiz") { PantallaQuiz(navController) }
        composable("resultado_prueba") { PantallaResultadoPrueba(navController) }
        composable("home_docente") { PantallaHomeDocente(navController) }
        composable("informe_pedagogico") { PantallaInformePedagogico(navController) }
        composable("home_utp") { PantallaHomeUTP(navController) }
    }
}