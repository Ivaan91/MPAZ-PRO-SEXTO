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
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp).clickable { navController.navigate("detalle_unidad/${item.id}") },
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