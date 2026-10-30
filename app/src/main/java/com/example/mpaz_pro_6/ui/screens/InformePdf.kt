package com.example.mpaz_pro_6.ui.screens

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mpaz_pro_6.model.Asignatura
import com.example.mpaz_pro_6.model.Unidad
import com.example.mpaz_pro_6.ui.theme.Paleta
import com.example.mpaz_pro_6.viewmodel.InformePedagogicoUiState
import java.io.ByteArrayOutputStream
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Botón "Descargar informe (PDF)". Usa el selector de archivos del sistema
// (CreateDocument), por lo que no necesita ningún permiso.
@Composable
fun BotonDescargarInforme(
    uiState: InformePedagogicoUiState,
    asignatura: Asignatura,
    unidad: Unidad
) {
    val contexto = LocalContext.current
    var mensaje by remember { mutableStateOf<String?>(null) }

    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri: Uri? ->
        mensaje = if (uri == null) {
            "Descarga cancelada."
        } else {
            try {
                val bytes = InformePdf.generar(uiState, asignatura, unidad)
                val salida = contexto.contentResolver.openOutputStream(uri)
                    ?: throw IllegalStateException("No se pudo abrir el archivo")
                salida.use { it.write(bytes) }
                "Informe guardado en formato PDF."
            } catch (e: Exception) {
                "No se pudo guardar el informe."
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = {
                mensaje = null
                lanzador.launch(nombreArchivo(asignatura))
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Descargar informe (PDF)")
        }
        mensaje?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, fontSize = 13.sp, color = Paleta.TextoSuave)
        }
    }
}

private fun nombreArchivo(asignatura: Asignatura): String {
    val sinTildes = Normalizer.normalize(asignatura.nombre, Normalizer.Form.NFD)
        .replace(Regex("\\p{M}+"), "")
    val limpio = sinTildes.replace(Regex("[^A-Za-z0-9]+"), "_").trim('_').take(24)
    val fecha = SimpleDateFormat("yyyyMMdd", Locale.US).format(Date())
    return "Informe_${limpio}_$fecha.pdf"
}

object InformePdf {

    fun generar(estado: InformePedagogicoUiState, asignatura: Asignatura, unidad: Unidad): ByteArray {
        val e = EscritorPdf()

        e.encabezado("Informe pedagógico", "Escuela Escritora Marcela Paz · ${estado.curso}")
        e.espacio(8f)
        e.parrafoNegrita("Estado del informe: ${estado.estado.texto}")

        e.seccion("Datos del informe")
        e.campo("Estudiante", "${estado.estudiante} (${estado.curso})")
        e.campo("Asignatura", asignatura.nombre)
        e.campo("Unidad", "${unidad.etiqueta} · ${unidad.titulo}")
        e.campo("Objetivo de aprendizaje", unidad.oa)
        e.campo("Docente responsable", estado.docenteResponsable)
        e.campo("Fecha de generación", estado.fechaGeneracion)

        e.seccion("Resumen")
        e.campo("Rendimiento general", "${estado.rendimientoGeneral}%")
        e.campo("Evaluaciones completadas", "${estado.evaluacionesCompletadas} de 3")
        e.campo("Respuestas correctas", "${estado.respuestasCorrectas}")
        e.campo("Respuestas incorrectas", "${estado.respuestasIncorrectas}")

        e.seccion("Rendimiento por etapa")
        estado.rendimientos.forEach { e.barra(it.nombre, it.porcentaje) }

        e.seccion("Fortalezas")
        if (estado.fortalezas.isEmpty()) {
            e.parrafo("Aún no se identifican contenidos con rendimiento igual o superior al 70 %.")
        } else {
            estado.fortalezas.forEach { e.punto(it) }
        }

        e.seccion("Contenidos por reforzar")
        if (estado.porReforzar.isEmpty()) {
            e.parrafo("No se detectaron contenidos bajo el 70 %.")
        } else {
            estado.porReforzar.forEach { e.punto(it) }
        }

        e.espacio(16f)
        e.parrafoSuave("Criterio: un contenido se considera logrado con 70 % o más de respuestas correctas.")
        e.parrafoSuave("Este informe se genera mediante reglas a partir de las respuestas del estudiante. No constituye un diagnóstico y requiere la validación del docente.")

        return e.bytes()
    }
}

// Escritor sencillo de PDF con saltos de página automáticos (A4 en puntos).
private class EscritorPdf {

    private val documento = PdfDocument()
    private var pagina: PdfDocument.Page? = null
    private var numero = 0
    private var y = MARGEN

    private val pTitulo = pintura(20f, true, 0xFF111827)
    private val pSeccion = pintura(14f, true, 0xFF1E3A8A)
    private val pTexto = pintura(11f, false, 0xFF1F2937)
    private val pNegrita = pintura(11f, true, 0xFF1F2937)
    private val pSuave = pintura(10f, false, 0xFF6B7280)
    private val pFondoBarra = pintura(11f, false, 0xFFE5E7EB)
    private val pRelleno = pintura(11f, false, 0xFF16A34A)

    init {
        nuevaPagina()
    }

    private val lienzo: Canvas
        get() = pagina!!.canvas

    private fun pintura(tamano: Float, enNegrita: Boolean, color: Long): Paint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = tamano
            isFakeBoldText = enNegrita
            this.color = color.toInt()
        }

    private fun nuevaPagina() {
        cerrarPagina()
        numero++
        val info = PdfDocument.PageInfo.Builder(ANCHO, ALTO, numero).create()
        pagina = documento.startPage(info)
        y = MARGEN
    }

    private fun cerrarPagina() {
        val actual = pagina ?: return
        actual.canvas.drawText(
            "MPAZ PRO 6.º · Datos ficticios · Página $numero",
            MARGEN,
            ALTO - 28f,
            pSuave
        )
        documento.finishPage(actual)
        pagina = null
    }

    private fun asegurar(altura: Float) {
        if (y + altura > ALTO - 56f) nuevaPagina()
    }

    private fun lineas(texto: String, pintura: Paint, anchoMax: Float): List<String> {
        val resultado = mutableListOf<String>()
        var actual = ""
        texto.split(" ").forEach { palabra ->
            val prueba = if (actual.isEmpty()) palabra else "$actual $palabra"
            if (actual.isEmpty() || pintura.measureText(prueba) <= anchoMax) {
                actual = prueba
            } else {
                resultado.add(actual)
                actual = palabra
            }
        }
        if (actual.isNotEmpty()) resultado.add(actual)
        return resultado
    }

    fun encabezado(titulo: String, subtitulo: String) {
        y += 22f
        lienzo.drawText(titulo, MARGEN, y, pTitulo)
        y += 18f
        lienzo.drawText(subtitulo, MARGEN, y, pSuave)
    }

    fun seccion(titulo: String) {
        asegurar(48f)
        y += 26f
        lienzo.drawText(titulo, MARGEN, y, pSeccion)
        y += 4f
    }

    fun campo(etiqueta: String, valor: String) {
        val lineasValor = lineas(valor, pTexto, ANCHO - 2 * MARGEN - ANCHO_ETIQUETA)
        lineasValor.forEachIndexed { indice, linea ->
            asegurar(18f)
            y += 16f
            if (indice == 0) lienzo.drawText(etiqueta, MARGEN, y, pSuave)
            lienzo.drawText(linea, MARGEN + ANCHO_ETIQUETA, y, pTexto)
        }
    }

    fun parrafo(texto: String) = escribir(texto, pTexto, 0f)

    fun parrafoNegrita(texto: String) = escribir(texto, pNegrita, 0f)

    fun parrafoSuave(texto: String) = escribir(texto, pSuave, 0f)

    fun punto(texto: String) {
        val lineasTexto = lineas(texto, pTexto, ANCHO - 2 * MARGEN - 14f)
        lineasTexto.forEachIndexed { indice, linea ->
            asegurar(18f)
            y += 16f
            if (indice == 0) lienzo.drawText("•", MARGEN + 2f, y, pTexto)
            lienzo.drawText(linea, MARGEN + 14f, y, pTexto)
        }
    }

    fun barra(nombre: String, porcentaje: Int) {
        asegurar(36f)
        y += 18f
        lienzo.drawText(nombre, MARGEN, y, pTexto)
        val valor = "$porcentaje%"
        lienzo.drawText(valor, ANCHO - MARGEN - pNegrita.measureText(valor), y, pNegrita)
        y += 6f
        val ancho = ANCHO - 2 * MARGEN
        lienzo.drawRoundRect(RectF(MARGEN, y, MARGEN + ancho, y + 7f), 3f, 3f, pFondoBarra)
        pRelleno.color = when {
            porcentaje >= 70 -> 0xFF16A34A.toInt()
            porcentaje >= 50 -> 0xFFF59E0B.toInt()
            else -> 0xFFDC2626.toInt()
        }
        if (porcentaje > 0) {
            lienzo.drawRoundRect(
                RectF(MARGEN, y, MARGEN + ancho * porcentaje.coerceIn(0, 100) / 100f, y + 7f),
                3f,
                3f,
                pRelleno
            )
        }
        y += 11f
    }

    fun espacio(altura: Float) {
        y += altura
    }

    private fun escribir(texto: String, pintura: Paint, sangria: Float) {
        lineas(texto, pintura, ANCHO - 2 * MARGEN - sangria).forEach { linea ->
            asegurar(18f)
            y += 16f
            lienzo.drawText(linea, MARGEN + sangria, y, pintura)
        }
    }

    fun bytes(): ByteArray {
        cerrarPagina()
        val salida = ByteArrayOutputStream()
        documento.writeTo(salida)
        documento.close()
        return salida.toByteArray()
    }

    private companion object {
        const val ANCHO = 595
        const val ALTO = 842
        const val MARGEN = 48f
        const val ANCHO_ETIQUETA = 150f
    }
}