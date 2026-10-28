package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.MockRepository

@Composable
fun PantallaDetalleUnidad(navController: NavHostController, asignaturaId: String) {
    val asignatura = MockRepository.asignaturaDe(asignaturaId)
    val unidad = MockRepository.unidadDe(asignaturaId)
    val cantidadPreguntas = MockRepository.preguntasDe(asignaturaId).size
    val colorAsignatura = Color(asignatura.colorHex)

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF8F9FA)).padding(20.dp).verticalScroll(rememberScrollState())) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = "Volver") }
            Column {
                Text(unidad.etiqueta, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(asignatura.nombre, fontSize = 12.sp, color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorAsignatura.copy(alpha = 0.12f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "${unidad.oa} · ${(asignatura.avance * 100).toInt()}% completado",
                    color = Color(0xFF374151),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Text(unidad.titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { asignatura.avance },
                    modifier = Modifier.fillMaxWidth(),
                    color = colorAsignatura,
                    trackColor = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Objetivo de aprendizaje", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF2563EB))
                Spacer(modifier = Modifier.height(4.dp))
                Text(unidad.objetivo, fontSize = 14.sp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Ruta de aprendizaje", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier.fillMaxWidth().clickable { navController.navigate("quiz/$asignaturaId") },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981))
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Contenido: ${unidad.contenido}", fontWeight = FontWeight.Bold)
                    Text(unidad.infoContenido, fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth().clickable { navController.navigate("quiz/$asignaturaId") },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF2563EB))
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Actividad: ${unidad.actividad}", fontWeight = FontWeight.Bold)
                    Text("$cantidadPreguntas preguntas · 5 min", fontSize = 12.sp, color = Color.Gray)
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
            }
        }
    }
}