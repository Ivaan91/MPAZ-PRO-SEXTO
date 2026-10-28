package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.MockRepository

@Composable
fun PantallaQuiz(navController: NavHostController, asignaturaId: String) {
    var opcionSeleccionada by remember { mutableIntStateOf(-1) }
    val pregunta = MockRepository.preguntasDe(asignaturaId).first()

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
            enabled = opcionSeleccionada >= 0,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Comprobar respuesta")
        }
    }
}