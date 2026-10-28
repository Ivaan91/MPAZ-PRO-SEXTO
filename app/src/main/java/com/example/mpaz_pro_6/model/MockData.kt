package com.example.mpaz_pro_6.model

data class Asignatura(
    val id: String,
    val nombre: String,
    val unidad: String,
    val avance: Float,
    val colorHex: Long
)

data class Unidad(
    val asignaturaId: String,
    val etiqueta: String,
    val titulo: String,
    val oa: String,
    val objetivo: String,
    val contenido: String,
    val infoContenido: String,
    val actividad: String
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

    val unidades = listOf(
        Unidad(
            asignaturaId = "1",
            etiqueta = "Unidad 2",
            titulo = "El texto narrativo",
            oa = "OA por confirmar",
            objetivo = "Leer textos narrativos e identificar narrador, personajes y conflicto.",
            contenido = "¿Qué es un texto narrativo?",
            infoContenido = "Lectura · 8 min",
            actividad = "Personajes y conflicto"
        ),
        Unidad(
            asignaturaId = "2",
            etiqueta = "Unidad 2",
            titulo = "Fracciones",
            oa = "OA por confirmar",
            objetivo = "Reconocer fracciones equivalentes y sumar fracciones con igual denominador.",
            contenido = "¿Qué es una fracción?",
            infoContenido = "Lectura · 8 min",
            actividad = "Operando con fracciones"
        ),
        Unidad(
            asignaturaId = "3",
            etiqueta = "Unidad 1",
            titulo = "Materia y sus transformaciones",
            oa = "OA 13",
            objetivo = "Distinguir sustancias puras y mezclas, y explicar cambios de estado usando ejemplos cotidianos.",
            contenido = "¿Qué es la materia?",
            infoContenido = "Lectura y audio · 8 min",
            actividad = "Estados de la materia"
        ),
        Unidad(
            asignaturaId = "4",
            etiqueta = "Unidad 2",
            titulo = "Chile republicano",
            oa = "OA por confirmar",
            objetivo = "Reconocer hechos y la organización política de Chile durante el siglo XIX.",
            contenido = "Los inicios de la república",
            infoContenido = "Lectura · 8 min",
            actividad = "La república en Chile"
        )
    )

    val preguntas: Map<String, List<PreguntaQuiz>> = mapOf(
        "1" to listOf(
            PreguntaQuiz(
                id = 11,
                preguntaNum = "Pregunta 1 de 2",
                enunciado = "En un cuento, ¿quién cuenta los hechos que les suceden a los personajes?",
                opciones = listOf("El narrador", "El lector", "El editor", "El ilustrador"),
                respuestaCorrectaIndex = 0
            ),
            PreguntaQuiz(
                id = 12,
                preguntaNum = "Pregunta 2 de 2",
                enunciado = "En un cuento, ¿cómo se llama la parte donde se resuelve el conflicto?",
                opciones = listOf("Inicio", "Desarrollo", "Desenlace", "Título"),
                respuestaCorrectaIndex = 2
            )
        ),
        "2" to listOf(
            PreguntaQuiz(
                id = 21,
                preguntaNum = "Pregunta 1 de 2",
                enunciado = "¿Cuál de estas fracciones es equivalente a 1/2?",
                opciones = listOf("2/4", "1/3", "2/3", "3/5"),
                respuestaCorrectaIndex = 0
            ),
            PreguntaQuiz(
                id = 22,
                preguntaNum = "Pregunta 2 de 2",
                enunciado = "¿Cuánto es 1/4 + 2/4?",
                opciones = listOf("3/8", "3/4", "2/4", "1/2"),
                respuestaCorrectaIndex = 1
            )
        ),
        "3" to listOf(
            PreguntaQuiz(
                id = 31,
                preguntaNum = "Pregunta 1 de 2",
                enunciado = "Al calentar agua en una olla, comienza a salir vapor. ¿Qué cambio de estado ocurrió?",
                opciones = listOf(
                    "Se derrite y pasa de sólido a líquido.",
                    "Se evapora y pasa de líquido a gas.",
                    "Se congela y pasa de líquido a sólido.",
                    "No cambia su estado físico."
                ),
                respuestaCorrectaIndex = 1
            ),
            PreguntaQuiz(
                id = 32,
                preguntaNum = "Pregunta 2 de 2",
                enunciado = "Cuando un hielo se derrite, ¿qué ocurre con su masa?",
                opciones = listOf("Aumenta", "Disminuye", "Se mantiene igual", "Desaparece"),
                respuestaCorrectaIndex = 2
            )
        ),
        "4" to listOf(
            PreguntaQuiz(
                id = 41,
                preguntaNum = "Pregunta 1 de 2",
                enunciado = "¿En qué año se proclamó oficialmente la Independencia de Chile?",
                opciones = listOf("1810", "1818", "1823", "1833"),
                respuestaCorrectaIndex = 1
            ),
            PreguntaQuiz(
                id = 42,
                preguntaNum = "Pregunta 2 de 2",
                enunciado = "¿Cómo se llama el documento que organiza el Estado y reconoce los derechos de las personas?",
                opciones = listOf("Constitución", "Calendario escolar", "Mapa político", "Censo"),
                respuestaCorrectaIndex = 0
            )
        )
    )

    fun asignaturaDe(id: String): Asignatura =
        asignaturas.firstOrNull { it.id == id } ?: asignaturas[2]

    fun unidadDe(id: String): Unidad =
        unidades.firstOrNull { it.asignaturaId == id } ?: unidades[2]

    fun preguntasDe(id: String): List<PreguntaQuiz> =
        preguntas[id] ?: preguntas.getValue("3")
}