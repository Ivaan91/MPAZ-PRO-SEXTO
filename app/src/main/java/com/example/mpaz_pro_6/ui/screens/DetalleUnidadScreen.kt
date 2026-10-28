package com.example.mpaz_pro_6.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.Etapa
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.ui.theme.Paleta

private enum class EstadoPaso { BLOQUEADO, DISPONIBLE, COMPLETADO }

@Composable
fun PantallaDetalleUnidad(navController: NavHostController, asignaturaId: String) {
    val asignatura = MockRepository.asignaturaDe(asignaturaId)
    val unidad = MockRepository.unidadDe(asignaturaId)
    val progreso = ProgresoRepository.de(asignaturaId)
    val avance = ProgresoRepository.avance(asignaturaId)
    val color = Color(asignatura.colorHex)

    val nContenidos = unidad.contenidos.size
    val leidos = progreso.contenidosLeidos.size
    val minutos = unidad.contenidos.sumOf { it.minutos }
    val hayAudio = unidad.contenidos.any { it.audio != null }
    val primerPendiente = (0 until nContenidos).firstOrNull { it !in progreso.contenidosLeidos } ?: 0

    val contenidosOk = ProgresoRepository.contenidosCompletos(asignaturaId)
    val actividadOk = progreso.actividad != null
    val desafioOk = progreso.desafio != null
    val intentosUsados = progreso.intentosPrueba.size
    val limite = ProgresoRepository.limiteIntentos(asignaturaId)
    val restantes = ProgresoRepository.intentosRestantes(asignaturaId)

    val nActividad = MockRepository.preguntasDe(asignaturaId, Etapa.ACTIVIDAD).size
    val nDesafio = MockRepository.preguntasDe(asignaturaId, Etapa.DESAFIO).size
    val nPrueba = MockRepository.preguntasDe(asignaturaId, Etapa.PRUEBA).size

    // Siguiente paso recomendado (botón inferior)
    val (textoBoton, destino) = when {
        !contenidosOk -> "Continuar contenido" to "contenido/$asignaturaId/$primerPendiente"
        !actividadOk -> "Continuar actividad" to "quiz/$asignaturaId/${Etapa.ACTIVIDAD.ruta}"
        !desafioOk -> "Continuar desafío" to "quiz/$asignaturaId/${Etapa.DESAFIO.ruta}"
        restantes > 0 && intentosUsados == 0 -> "Rendir prueba final" to "quiz/$asignaturaId/${Etapa.PRUEBA.ruta}"
        restantes > 0 -> "Intentar la prueba otra vez" to "quiz/$asignaturaId/${Etapa.PRUEBA.ruta}"
        else -> "Ver resultado de la prueba" to "resultado/$asignaturaId/${Etapa.PRUEBA.ruta}"
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
        ) {
            Row(modifier = Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Paleta.Texto)
                }
                Column {
                    Text(unidad.etiqueta, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Paleta.Texto)
                    Text(asignatura.nombre, fontSize = 12.sp, color = Paleta.TextoSuave)
                }
            }

            Spacer(Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "${unidad.oa} · ${(avance * 100).toInt()}% completado",
                        color = Color(0xFF374151),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        unidad.titulo,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Paleta.Texto,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { avance },
                        modifier = Modifier.fillMaxWidth(),
                        color = color,
                        trackColor = Color.White
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Objetivo de aprendizaje", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Paleta.Azul)
                    Spacer(Modifier.height(4.dp))
                    Text(unidad.objetivo, fontSize = 14.sp, color = Paleta.Texto)
                }
            }

            Spacer(Modifier.height(20.dp))
            Text("Ruta de aprendizaje", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Paleta.Texto)
            Spacer(Modifier.height(8.dp))

            PasoRuta(
                icono = Icons.Default.MenuBook,
                etiqueta = "Contenido",
                titulo = if (nContenidos == 1) unidad.contenidos.first().titulo else "Contenidos ($leidos de $nContenidos)",
                detalle = (if (hayAudio) "Lectura y audio" else "Lectura") + " · $minutos min",
                estado = if (contenidosOk) EstadoPaso.COMPLETADO else EstadoPaso.DISPONIBLE,
                onClick = { navController.navigate("contenido/$asignaturaId/$primerPendiente") }
            )
            PasoRuta(
                icono = Icons.Default.Edit,
                etiqueta = "Actividad",
                titulo = Etapa.ACTIVIDAD.titulo,
                detalle = when {
                    !contenidosOk -> "Lee los contenidos para desbloquearla"
                    actividadOk -> "${progreso.actividad!!.correctas} de ${progreso.actividad.total} correctas · puedes repetirla"
                    else -> "$nActividad preguntas · con retroalimentación"
                },
                estado = when {
                    actividadOk -> EstadoPaso.COMPLETADO
                    contenidosOk -> EstadoPaso.DISPONIBLE
                    else -> EstadoPaso.BLOQUEADO
                },
                onClick = { navController.navigate("quiz/$asignaturaId/${Etapa.ACTIVIDAD.ruta}") }
            )
            PasoRuta(
                icono = Icons.Default.EmojiEvents,
                etiqueta = "Desafío",
                titulo = Etapa.DESAFIO.titulo,
                detalle = when {
                    !actividadOk -> "Completa la actividad para desbloquearlo"
                    desafioOk -> "${progreso.desafio!!.correctas} de ${progreso.desafio.total} correctas · puedes repetirlo"
                    else -> "$nDesafio preguntas de aplicación"
                },
                estado = when {
                    desafioOk -> EstadoPaso.COMPLETADO
                    actividadOk -> EstadoPaso.DISPONIBLE
                    else -> EstadoPaso.BLOQUEADO
                },
                onClick = { navController.navigate("quiz/$asignaturaId/${Etapa.DESAFIO.ruta}") }
            )
            PasoRuta(
                icono = Icons.Default.Quiz,
                etiqueta = "Prueba final",
                titulo = "Comprueba lo aprendido",
                detalle = if (!desafioOk) {
                    "Completa el desafío para desbloquearla"
                } else {
                    "$nPrueba preguntas · $restantes de $limite intentos disponibles"
                },
                estado = when {
                    !desafioOk -> EstadoPaso.BLOQUEADO
                    intentosUsados > 0 -> EstadoPaso.COMPLETADO
                    else -> EstadoPaso.DISPONIBLE
                },
                onClick = {
                    val ruta = if (restantes > 0) "quiz/$asignaturaId/${Etapa.PRUEBA.ruta}" else "resultado/$asignaturaId/${Etapa.PRUEBA.ruta}"
                    navController.navigate(ruta)
                }
            )
            Spacer(Modifier.height(12.dp))
        }

        Button(
            onClick = { navController.navigate(destino) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Paleta.Azul, contentColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(textoBoton)
        }
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun PasoRuta(
    icono: ImageVector,
    etiqueta: String,
    titulo: String,
    detalle: String,
    estado: EstadoPaso,
    onClick: () -> Unit
) {
    val bloqueado = estado == EstadoPaso.BLOQUEADO
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(enabled = !bloqueado, onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = if (bloqueado) Paleta.Bloqueado else Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            when (estado) {
                EstadoPaso.COMPLETADO -> Icon(Icons.Default.CheckCircle, contentDescription = "Completado", tint = Paleta.Verde)
                EstadoPaso.BLOQUEADO -> Icon(Icons.Default.Lock, contentDescription = "Bloqueado", tint = Paleta.TextoSuave)
                EstadoPaso.DISPONIBLE -> Icon(icono, contentDescription = null, tint = Paleta.Azul)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(etiqueta, fontSize = 11.sp, color = Paleta.TextoSuave)
                Text(
                    titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = if (bloqueado) Paleta.TextoSuave else Paleta.Texto
                )
                Text(detalle, fontSize = 12.sp, color = Paleta.TextoSuave)
            }
            if (!bloqueado) {
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Paleta.TextoSuave)
            }
        }
    }
}