package com.example.mpaz_pro_6.model

data class Asignatura(
    val id: String,
    val nombre: String,
    val unidad: String,
    val avance: Float,
    val colorHex: Long
)

data class PreguntaQuiz(
    val id: Int,
    val preguntaNum: String,
    val enunciado: String,
    val opciones: List<String>,
    val respuestaCorrectaIndex: Int
)

object MockRepository {
    val asignaturas = listOf(
        Asignatura("1", "Lenguaje y Comunicación", "Unidad 2 · Narrativa", 0.70f, 0xFFEF4444),
        Asignatura("2", "Matemática", "Unidad 2 · Fracciones", 0.40f, 0xFF3B82F6),
        Asignatura("3", "Ciencias Naturales", "Unidad 1 · Materia", 0.80f, 0xFF10B981),
        Asignatura("4", "Historia, Geografía y Ciencias Sociales", "Unidad 2 · Chile republicano", 0.20f, 0xFFF59E0B)
    )

    val preguntasCiencias = listOf(
        PreguntaQuiz(
            id = 1,
            preguntaNum = "Pregunta 4 de 8",
            enunciado = "Al calentar agua en una olla, comienza a salir vapor. ¿Qué cambio de estado ocurrió?",
            opciones = listOf(
                "Se derrite y pasa de sólido a líquido.",
                "Se evapora y pasa de líquido a gas.",
                "Se congela y pasa de líquido a sólido.",
                "No cambia su estado físico."
            ),
            respuestaCorrectaIndex = 1
        )
    )
}