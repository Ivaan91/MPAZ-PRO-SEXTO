package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

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