package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun PantallaHomeDocente(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(44.dp).background(Color(0xFFFEF3C7), RoundedCornerShape(22.dp)), contentAlignment = Alignment.Center) {
                Text("JM", color = Color(0xFFB45309), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Vista docente", fontSize = 11.sp, color = Color.Gray)
                Text("Hola, Javier", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A)), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Avance promedio del curso", color = Color.White, fontSize = 13.sp)
                Text("67%", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("24 activos", color = Color.White, fontSize = 12.sp)
                    Text("18 al día", color = Color.White, fontSize = 12.sp)
                    Text("4 pendientes", color = Color.White, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().clickable { navController.navigate("informe_pedagogico") },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("3 informes pendientes de validar", fontWeight = FontWeight.Bold, color = Color(0xFF92400E), fontSize = 14.sp)
                    Text("Revisa los resultados antes de compartirlos.", fontSize = 12.sp, color = Color(0xFFB45309))
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF92400E))
            }
        }
    }
}

@Composable
fun PantallaInformePedagogico(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(20.dp).verticalScroll(rememberScrollState())) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
            Column {
                Text("Informe pedagógico", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Vista exclusiva del equipo docente", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A)), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Resumen de la unidad", color = Color.White, fontSize = 13.sp)
                Text("68% rendimiento", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Rendimiento por objetivo", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(10.dp))
        Text("OA 13 Sustancias y mezclas - 82%", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        LinearProgressIndicator(progress = { 0.82f }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), color = Color(0xFF10B981))

        Spacer(modifier = Modifier.height(10.dp))
        Text("OA 14 Cambios de estado - 68%", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        LinearProgressIndicator(progress = { 0.68f }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), color = Color(0xFFF59E0B))

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Validar informe")
        }
    }
}

@Composable
fun PantallaHomeUTP(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
            Column {
                Text("Revisión UTP", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Unidad enviada por Javier Muñoz", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A)), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Ciencias Naturales", color = Color(0xFF93C5FD), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text("Materia y sus transformaciones", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Lista de verificación", fontWeight = FontWeight.Bold, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(12.dp))
        Text("✓ Objetivo de aprendizaje alineado", fontSize = 14.sp)
        Text("✓ Contenidos y actividades completos", fontSize = 14.sp)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Autorizar unidad")
        }
    }
}