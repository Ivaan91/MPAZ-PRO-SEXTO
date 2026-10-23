package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.data.UsuarioDao
import com.example.mpaz_pro_6.data.UsuarioEntity

@Composable
fun PantallaLogin(navController: NavHostController, usuarioDao: UsuarioDao) {
    var usuarios by remember { mutableStateOf<List<UsuarioEntity>>(emptyList()) }
    var usuarioSeleccionado by remember { mutableStateOf<UsuarioEntity?>(null) }

    LaunchedEffect(Unit) {
        var lista = usuarioDao.obtenerTodosLosUsuarios()
        if (lista.isEmpty()) {
            val inicialesFicticios = listOf(
                UsuarioEntity(iniciales = "SC", nombre = "Sofía Contreras", descripcion = "Estudiante · 6.º A", rol = "Estudiante"),
                UsuarioEntity(iniciales = "JM", nombre = "Javier Muñoz", descripcion = "Profesor jefe · 6.º A", rol = "Docente"),
                UsuarioEntity(iniciales = "AV", nombre = "Andrea Vidal", descripcion = "Unidad Técnico Pedagógica", rol = "UTP")
            )
            usuarioDao.insertarUsuarios(inicialesFicticios)
            lista = usuarioDao.obtenerTodosLosUsuarios()
        }
        usuarios = lista
        usuarioSeleccionado = lista.firstOrNull { it.rol == "Estudiante" }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.32f)
                .background(Color(0xFF1E3A8A), RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.Default.MenuBook, contentDescription = "Logo", tint = Color.White, modifier = Modifier.size(44.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Text("MPAZ PRO 6.º", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("Escuela Escritora Marcela Paz - Apoyo didáctico", color = Color.White, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text("¿Quién va a ingresar?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text("Selecciona un perfil ficticio para revisar el prototipo.", fontSize = 13.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            usuarios.forEach { user ->
                val isSelected = user == usuarioSeleccionado
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { usuarioSeleccionado = user }
                        .border(2.dp, if (isSelected) Color(0xFF2563EB) else Color.LightGray, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFEFF6FF) else Color.White)
                ) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(44.dp).background(Color.White, RoundedCornerShape(22.dp)).border(1.dp, Color.LightGray, RoundedCornerShape(22.dp)),
                            contentAlignment = Alignment.Center
                        ) { Text(user.iniciales, color = Color(0xFF1E3A8A), fontWeight = FontWeight.Bold) }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(user.nombre, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text(user.descripcion, fontSize = 12.sp, color = Color.Gray)
                        }
                        RadioButton(
                            selected = isSelected,
                            onClick = { usuarioSeleccionado = user },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2563EB))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    when (usuarioSeleccionado?.rol) {
                        "Estudiante" -> navController.navigate("home_estudiante")
                        "Docente" -> navController.navigate("home_docente")
                        "UTP" -> navController.navigate("home_utp")
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ingresar como ${usuarioSeleccionado?.rol?.lowercase() ?: ""}")
            }
        }
    }
}