package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.Etapa
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.ui.theme.Paleta

@Composable
fun PantallaResultadoPrueba(navController: NavHostController, asignaturaId: String, etapa: Etapa) {
    val progreso = ProgresoRepository.de(asignaturaId)
    val resultado = when (etapa) {
        Etapa.ACTIVIDAD -> progreso.actividad
        Etapa.DESAFIO -> progreso.desafio
        Etapa.PRUEBA -> progreso.intentosPrueba.lastOrNull()
    }

    if (resultado == null) {
        LaunchedEffect(Unit) { navController.popBackStack() }
        return
    }

    val restantes = ProgresoRepository.intentosRestantes(asignaturaId)
    val incorrectas = resultado.total - resultado.correctas
    val mensaje = if (resultado.porcentaje >= 60) "¡Buen trabajo, Sofía!" else "¡Sigue practicando, Sofía!"

    fun irA(ruta: String) {
        navController.navigate(ruta) {
            popUpTo("resultado/{asignaturaId}/{etapa}") { inclusive = true }
        }
    }

    fun volverALaUnidad() {
        if (!navController.popBackStack("detalle_unidad/{asignaturaId}", false)) {
            navController.navigate("detalle_unidad/$asignaturaId")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Paleta.Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Paleta.AzulOscuro),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(etapa.titulo, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    Text("${resultado.porcentaje}%", color = Color.White, fontSize = 42.sp, fontWeight = FontWeight.Bold)
                    Text("${resultado.correctas} de ${resultado.total}", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(mensaje, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        if (etapa == Etapa.PRUEBA) "Completaste la prueba. Revisa tus respuestas antes de intentarlo otra vez."
                        else "Revisa tus respuestas y sigue con el recorrido.",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    // Confirmación de que la etapa quedó registrada
                    Surface(
                        color = if (etapa == Etapa.PRUEBA) Paleta.AmbarClaro else Paleta.VerdeClaro,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            if (etapa == Etapa.PRUEBA) {
                                if (restantes == 1) "1 intento restante" else "$restantes intentos restantes"
                            } else {
                                "${etapa.titulo} completada"
                            },
                            color = if (etapa == Etapa.PRUEBA) Paleta.AmbarOscuro else Paleta.VerdeOscuro,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Paleta.VerdeClaro),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${resultado.correctas}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Paleta.VerdeOscuro)
                        Text("Correctas", fontSize = 13.sp, color = Paleta.Texto)
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Paleta.RojoClaro),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("$incorrectas", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Paleta.RojoOscuro)
                        Text("Incorrectas", fontSize = 13.sp, color = Paleta.Texto)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Text("Revisión de respuestas", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Paleta.Texto)
            Spacer(Modifier.height(8.dp))

            resultado.preguntas.forEachIndexed { i, pregunta ->
                val acerto = resultado.elegidas.getOrNull(i) == pregunta.correcta
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${i + 1}. ${pregunta.tema}", fontSize = 14.sp, color = Paleta.Texto, modifier = Modifier.weight(1f))
                    Text(
                        if (acerto) "Correcta" else "Incorrecta",
                        color = if (acerto) Paleta.Verde else Paleta.Rojo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
                if (!acerto) {
                    Text(
                        "Respuesta correcta: ${pregunta.opciones[pregunta.correcta]}",
                        fontSize = 12.sp,
                        color = Paleta.TextoSuave,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(top = 8.dp), color = Paleta.Borde)
            }
            Spacer(Modifier.height(12.dp))
        }

        // Acciones según la etapa
        when (etapa) {
            Etapa.ACTIVIDAD -> BotonPrimario("Continuar al desafío") { irA("quiz/$asignaturaId/${Etapa.DESAFIO.ruta}") }
            Etapa.DESAFIO -> BotonPrimario("Ir a la prueba final") { irA("quiz/$asignaturaId/${Etapa.PRUEBA.ruta}") }
            Etapa.PRUEBA -> if (restantes > 0) {
                BotonPrimario("Intentar otra vez") { irA("quiz/$asignaturaId/${Etapa.PRUEBA.ruta}") }
            }
        }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(
            onClick = { volverALaUnidad() },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Volver a la unidad", color = Paleta.Azul)
        }
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun BotonPrimario(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Paleta.Azul, contentColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(texto)
    }
}