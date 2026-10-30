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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.EstadoListaInforme
import com.example.mpaz_pro_6.model.InformesResumen
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.ui.theme.Paleta

@Composable
fun PantallaListaInformes(navController: NavHostController) {
    val pendientes = InformesResumen.pendientes()
    val validados = InformesResumen.validados()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Paleta.Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Paleta.Texto)
            }
            Column {
                Text("Informes", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Paleta.Texto)
                Text("Vista exclusiva del equipo docente", fontSize = 12.sp, color = Paleta.TextoSuave)
            }
        }
        HorizontalDivider(color = Paleta.Borde)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text("Sofía Contreras · 6.º A", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Paleta.Texto)
            Text("Estudiante de demostración", fontSize = 12.sp, color = Paleta.TextoSuave)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "$pendientes pendientes de validar · $validados validados",
                fontSize = 13.sp,
                color = Paleta.AmbarOscuro,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(16.dp))

            MockRepository.asignaturas.forEach { item ->
                val unidad = MockRepository.unidadDe(item.id)
                val estado = InformesResumen.estado(item.id)
                val rendimiento = InformesResumen.rendimiento(item.id)
                val habilitado = estado != EstadoListaInforme.SIN_RESULTADOS

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable(enabled = habilitado) {
                            navController.navigate("informe_pedagogico/${item.id}")
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Paleta.Texto)
                            Text(
                                "${unidad.etiqueta} · ${unidad.titulo}",
                                fontSize = 12.sp,
                                color = Paleta.TextoSuave
                            )
                            Text(unidad.oa, fontSize = 11.sp, color = Paleta.TextoSuave)
                            Spacer(modifier = Modifier.height(8.dp))
                            EtiquetaEstado(estado)
                            Spacer(modifier = Modifier.height(8.dp))
                            if (rendimiento != null) {
                                LinearProgressIndicator(
                                    progress = { rendimiento / 100f },
                                    modifier = Modifier.fillMaxWidth().height(6.dp),
                                    color = Color(item.colorHex),
                                    trackColor = Paleta.Borde
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Rendimiento: $rendimiento%", fontSize = 12.sp, color = Paleta.TextoSuave)
                            } else {
                                Text(
                                    "El estudiante aún no realiza actividades de esta unidad.",
                                    fontSize = 12.sp,
                                    color = Paleta.TextoSuave
                                )
                            }
                        }
                        if (habilitado) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.Default.ChevronRight, contentDescription = "Abrir informe", tint = Paleta.TextoSuave)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun EtiquetaEstado(estado: EstadoListaInforme) {
    val (fondo, texto) = when (estado) {
        EstadoListaInforme.SIN_RESULTADOS -> Paleta.Bloqueado to Paleta.TextoSuave
        EstadoListaInforme.PENDIENTE -> Paleta.AmbarClaro to Paleta.AmbarOscuro
        EstadoListaInforme.VALIDADO -> Paleta.VerdeClaro to Paleta.VerdeOscuro
    }
    Box(
        modifier = Modifier
            .background(fondo, RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(estado.texto, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = texto)
    }
}