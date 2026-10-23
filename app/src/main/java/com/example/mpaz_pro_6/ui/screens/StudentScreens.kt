package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.mpaz_pro_6.model.MockRepository

@Composable
fun PantallaHomeEstudiante(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(44.dp).background(Color.White, RoundedCornerShape(22.dp)).border(1.dp, Color.LightGray, RoundedCornerShape(22.dp)), contentAlignment = Alignment.Center) {
                    Text("SC", color = Color(0xFF1E3A8A), fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Lunes 5 de octubre", fontSize = 11.sp, color = Color.Gray)
                    Text("¡Hola, Sofía!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A)), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text("Tu avance esta semana", color = Color.White, fontSize = 13.sp)
                Text("64%", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(progress = { 0.64f }, modifier = Modifier.fillMaxWidth(), color = Color(0xFFF59E0B), trackColor = Color.White.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(10.dp))
                Text("Vas muy bien. Te faltan 2 actividades para tu meta.", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Tus asignaturas", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        MockRepository.asignaturas.forEach { item ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp).clickable { navController.navigate("detalle_unidad") },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(item.unidad, fontSize = 12.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(progress = { item.avance }, modifier = Modifier.fillMaxWidth(0.6f), color = Color(item.colorHex))
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun PantallaDetalleUnidad(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(20.dp).verticalScroll(rememberScrollState())) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
            Column {
                Text("Unidad 1", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Ciencias Naturales", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F4EA)), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("OA 13 - 35% completado", color = Color(0xFF137333), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text("Materia y sus transformaciones", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Ruta de aprendizaje", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth().clickable { navController.navigate("quiz") },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981))
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Contenido: ¿Qué es la materia?", fontWeight = FontWeight.Bold)
                    Text("Lectura y audio · 8 min", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth().clickable { navController.navigate("quiz") },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF2563EB))
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Actividad: Estados de la materia", fontWeight = FontWeight.Bold)
                    Text("8 preguntas · 10 min", fontSize = 12.sp, color = Color.Gray)
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
            }
        }
    }
}

@Composable
fun PantallaQuiz(navController: NavHostController) {
    var opcionSeleccionada by remember { mutableIntStateOf(1) }
    val pregunta = MockRepository.preguntasCiencias[0]

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(20.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = null) }
            Text(pregunta.preguntaNum, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.Close, contentDescription = null) }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(pregunta.enunciado, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        pregunta.opciones.forEachIndexed { index, opcion ->
            val selected = index == opcionSeleccionada
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { opcionSeleccionada = index }
                    .border(2.dp, if (selected) Color(0xFF2563EB) else Color.Transparent, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = if (selected) Color(0xFFEFF6FF) else Color.White)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("${('A' + index)}.", fontWeight = FontWeight.Bold, color = if (selected) Color(0xFF2563EB) else Color.Gray)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(opcion, modifier = Modifier.weight(1f), fontSize = 14.sp)
                    RadioButton(selected = selected, onClick = { opcionSeleccionada = index })
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate("resultado_prueba") },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Comprobar respuesta")
        }
    }
}

@Composable
fun PantallaResultadoPrueba(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(20.dp)) {
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A)), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("70%", color = Color.White, fontSize = 42.sp, fontWeight = FontWeight.Bold)
                Text("7 de 10", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text("¡Buen trabajo, Sofía!", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Completaste la prueba. Revisa tus respuestas antes de intentarlo otra vez.", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Revisión de respuestas", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("1. Propiedades de la materia", fontSize = 14.sp)
            Text("Correcta", color = Color(0xFF10B981), fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("3. Cambio de estado", fontSize = 14.sp)
            Text("Incorrecta", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate("home_estudiante") },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Volver a la unidad")
        }
    }
}