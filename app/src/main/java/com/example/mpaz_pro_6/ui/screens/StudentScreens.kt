package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
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
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.ui.theme.Paleta

@Composable
fun PantallaHomeEstudiante(navController: NavHostController) {
    val avanceGeneral = ProgresoRepository.avanceGeneral()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Paleta.Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color.White, RoundedCornerShape(22.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(22.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("SC", color = Paleta.AzulOscuro, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Lunes 5 de octubre", fontSize = 11.sp, color = Paleta.TextoSuave)
                    Text("¡Hola, Sofía!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Paleta.Texto)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Paleta.AzulOscuro),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text("Tu avance general", color = Color.White, fontSize = 13.sp)
                Text("${(avanceGeneral * 100).toInt()}%", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { avanceGeneral },
                    modifier = Modifier.fillMaxWidth(),
                    color = Paleta.Ambar,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    if (avanceGeneral == 0f) "Empieza por la asignatura que prefieras." else "Vas muy bien. ¡Sigue avanzando!",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Tus asignaturas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Paleta.Texto)
        Spacer(modifier = Modifier.height(8.dp))

        MockRepository.asignaturas.forEach { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clickable { navController.navigate("detalle_unidad/${item.id}") },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Paleta.Texto)
                        Text(item.unidad, fontSize = 12.sp, color = Paleta.TextoSuave)
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { ProgresoRepository.avance(item.id) },
                            modifier = Modifier.fillMaxWidth(0.6f),
                            color = Color(item.colorHex),
                            trackColor = Paleta.Borde
                        )
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Paleta.TextoSuave)
                }
            }
        }
    }
}
