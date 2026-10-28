package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.Etapa
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.ui.theme.Paleta

@Composable
fun PantallaContenido(navController: NavHostController, asignaturaId: String, indice: Int) {
    val asignatura = MockRepository.asignaturaDe(asignaturaId)
    val unidad = MockRepository.unidadDe(asignaturaId)
    val contenido = unidad.contenidos.getOrNull(indice)
    var sonando by remember { mutableStateOf(false) }

    if (contenido == null) {
        LaunchedEffect(Unit) { navController.popBackStack() }
        return
    }

    val total = unidad.contenidos.size
    val esUltimo = indice == total - 1
    val color = Color(asignatura.colorHex)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Paleta.Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        // Encabezado
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Paleta.Texto)
            }
            Column {
                Text(asignatura.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Paleta.Texto)
                Text("Contenido ${indice + 1} de $total", fontSize = 12.sp, color = Paleta.TextoSuave)
            }
        }

        // Cuerpo con scroll
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 12.dp)
        ) {
            Surface(color = Paleta.VerdeClaro, shape = RoundedCornerShape(20.dp)) {
                Text(
                    "Lectura · ${contenido.minutos} min",
                    color = Paleta.VerdeOscuro,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(Modifier.height(14.dp))
            Text(contenido.titulo, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Paleta.Texto)
            Spacer(Modifier.height(8.dp))
            Text(contenido.texto, fontSize = 15.sp, lineHeight = 22.sp, color = Paleta.TextoSuave)

            // Recurso visual
            if (contenido.visual.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            contenido.visualTitulo ?: "En resumen",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Paleta.Texto
                        )
                        Spacer(Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            contenido.visual.forEach { (titulo, detalle) ->
                                Card(
                                    modifier = Modifier.weight(1f),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(titulo, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Paleta.Texto, textAlign = TextAlign.Center)
                                        Text(detalle, fontSize = 12.sp, color = Paleta.TextoSuave, textAlign = TextAlign.Center)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Audio opcional (demostración visual: aún no reproduce un archivo real)
            contenido.audio?.let { duracion ->
                Spacer(Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        FilledIconButton(
                            onClick = { sonando = !sonando },
                            colors = IconButtonDefaults.filledIconButtonColors(containerColor = Paleta.Azul, contentColor = Color.White)
                        ) {
                            Icon(
                                if (sonando) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (sonando) "Pausar explicación" else "Escuchar explicación"
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Escuchar explicación", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Paleta.Texto)
                            Text(
                                if (sonando) "Reproduciendo… (demostración)" else "Audio opcional · $duracion",
                                fontSize = 12.sp,
                                color = Paleta.TextoSuave
                            )
                        }
                    }
                }
            }

            // Ejemplo
            Spacer(Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Ejemplo", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Paleta.Azul)
                    Spacer(Modifier.height(4.dp))
                    Text(contenido.ejemplo, fontSize = 14.sp, color = Paleta.Texto)
                }
            }

            // Idea clave
            Spacer(Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Paleta.AmbarClaro),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Paleta.Ambar)
                    Spacer(Modifier.width(10.dp))
                    Text(
                        "Idea clave: ${contenido.ideaClave}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Paleta.AmbarOscuro
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        Button(
            onClick = {
                ProgresoRepository.marcarContenido(asignaturaId, indice)
                val destino = if (esUltimo) {
                    "quiz/$asignaturaId/${Etapa.ACTIVIDAD.ruta}"
                } else {
                    "contenido/$asignaturaId/${indice + 1}"
                }
                navController.navigate(destino) {
                    popUpTo("contenido/{asignaturaId}/{indice}") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Paleta.Azul, contentColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(if (esUltimo) "Ir a la actividad" else "Siguiente contenido")
        }
        Spacer(Modifier.height(12.dp))
    }
}