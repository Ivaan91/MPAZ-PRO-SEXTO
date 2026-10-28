package com.example.mpaz_pro_6.model

import androidx.compose.runtime.mutableStateMapOf

/** Resultado de una etapa: lo que eligió el estudiante frente a las preguntas. */
data class ResultadoEtapa(
    val elegidas: List<Int>,
    val preguntas: List<PreguntaQuiz>
) {
    val total: Int get() = preguntas.size
    val correctas: Int get() = preguntas.indices.count { elegidas.getOrNull(it) == preguntas[it].correcta }
    val porcentaje: Int get() = if (total == 0) 0 else correctas * 100 / total
}

data class ProgresoUnidad(
    val contenidosLeidos: Set<Int> = emptySet(),
    val actividad: ResultadoEtapa? = null,
    val desafio: ResultadoEtapa? = null,
    val intentosPrueba: List<ResultadoEtapa> = emptyList()
)

/**
 * Progreso del estudiante guardado en memoria (se reinicia al cerrar la app).
 * Al ser estado de Compose, las pantallas se actualizan solas cuando cambia.
 */
object ProgresoRepository {
    private val estado = mutableStateMapOf<String, ProgresoUnidad>()

    // Límite de intentos de la prueba final por asignatura (lo configurará el docente en el paso 3).
    private val limites = mutableStateMapOf("1" to 3, "2" to 3, "3" to 3, "4" to 3)

    fun de(asignaturaId: String): ProgresoUnidad = estado[asignaturaId] ?: ProgresoUnidad()

    // --- Intentos de la prueba final ---
    fun limiteIntentos(asignaturaId: String): Int = limites[asignaturaId] ?: 3

    fun configurarLimiteIntentos(asignaturaId: String, limite: Int) {
        limites[asignaturaId] = limite.coerceIn(1, 10)
    }

    fun intentosUsados(asignaturaId: String): Int = de(asignaturaId).intentosPrueba.size

    fun intentosRestantes(asignaturaId: String): Int =
        (limiteIntentos(asignaturaId) - intentosUsados(asignaturaId)).coerceAtLeast(0)

    // --- Registro del avance ---
    fun marcarContenido(asignaturaId: String, indice: Int) {
        val p = de(asignaturaId)
        estado[asignaturaId] = p.copy(contenidosLeidos = p.contenidosLeidos + indice)
    }

    fun guardarResultado(asignaturaId: String, etapa: Etapa, resultado: ResultadoEtapa) {
        val p = de(asignaturaId)
        estado[asignaturaId] = when (etapa) {
            Etapa.ACTIVIDAD -> p.copy(actividad = resultado)
            Etapa.DESAFIO -> p.copy(desafio = resultado)
            Etapa.PRUEBA ->
                if (intentosRestantes(asignaturaId) > 0) p.copy(intentosPrueba = p.intentosPrueba + resultado) else p
        }
    }

    // --- Consultas de estado ---
    fun contenidosCompletos(asignaturaId: String): Boolean =
        de(asignaturaId).contenidosLeidos.size >= MockRepository.unidadDe(asignaturaId).contenidos.size

    /** Cada etapa se desbloquea al completar la anterior. */
    fun desbloqueada(asignaturaId: String, etapa: Etapa): Boolean {
        val p = de(asignaturaId)
        return when (etapa) {
            Etapa.ACTIVIDAD -> contenidosCompletos(asignaturaId)
            Etapa.DESAFIO -> p.actividad != null
            Etapa.PRUEBA -> p.desafio != null
        }
    }

    fun avance(asignaturaId: String): Float {
        val p = de(asignaturaId)
        val pasos = MockRepository.unidadDe(asignaturaId).contenidos.size + 3
        val hechos = p.contenidosLeidos.size +
                (if (p.actividad != null) 1 else 0) +
                (if (p.desafio != null) 1 else 0) +
                (if (p.intentosPrueba.isNotEmpty()) 1 else 0)
        return (hechos.toFloat() / pasos).coerceIn(0f, 1f)
    }

    fun avanceGeneral(): Float =
        MockRepository.asignaturas.map { avance(it.id) }.average().toFloat()

    fun reiniciar() = estado.clear()
}