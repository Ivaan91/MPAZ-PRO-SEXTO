package com.example.mpaz_pro_6.model

enum class Etapa(val ruta: String, val titulo: String) {
    ACTIVIDAD("actividad", "Actividad de práctica"),
    DESAFIO("desafio", "Desafío aplicado"),
    PRUEBA("prueba", "Prueba final");

    companion object {
        fun desdeRuta(ruta: String): Etapa = entries.firstOrNull { it.ruta == ruta } ?: ACTIVIDAD
    }
}

data class Asignatura(
    val id: String,
    val nombre: String,
    val unidad: String,
    val colorHex: Long
)

data class Contenido(
    val titulo: String,
    val texto: String,
    val ejemplo: String,
    val ideaClave: String,
    val minutos: Int,
    val audio: String? = null,
    val visualTitulo: String? = null,
    val visual: List<Pair<String, String>> = emptyList()
)

data class Unidad(
    val asignaturaId: String,
    val etiqueta: String,
    val titulo: String,
    val oa: String,
    val objetivo: String,
    val contenidos: List<Contenido>
)

data class PreguntaQuiz(
    val tema: String,
    val enunciado: String,
    val opciones: List<String>,
    val correcta: Int
)

object MockRepository {
    val asignaturas = listOf(
        Asignatura("1", "Lenguaje y Comunicación", "Unidad 2 · Narrativa", 0xFFEF4444),
        Asignatura("2", "Matemática", "Unidad 2 · Fracciones", 0xFF3B82F6),
        Asignatura("3", "Ciencias Naturales", "Unidad 1 · Materia", 0xFF10B981),
        Asignatura("4", "Historia, Geografía y Ciencias Sociales", "Unidad 2 · Chile republicano", 0xFFF59E0B)
    )

    val unidades = listOf(
        Unidad(
            asignaturaId = "1",
            etiqueta = "Unidad 2",
            titulo = "El texto narrativo",
            oa = "OA por confirmar",
            objetivo = "Leer textos narrativos e identificar narrador, personajes y conflicto.",
            contenidos = listOf(
                Contenido(
                    titulo = "¿Qué es un texto narrativo?",
                    texto = "Un texto narrativo cuenta una historia en la que les suceden hechos a uno o más personajes. " +
                            "Lo cuenta un narrador y suele organizarse en inicio, desarrollo y desenlace.",
                    ejemplo = "En un cuento de aventuras, el inicio presenta a los personajes y el lugar, " +
                            "el desarrollo muestra el conflicto y el desenlace lo resuelve.",
                    ideaClave = "Todo texto narrativo tiene narrador, personajes y un conflicto.",
                    minutos = 8
                )
            )
        ),
        Unidad(
            asignaturaId = "2",
            etiqueta = "Unidad 2",
            titulo = "Fracciones",
            oa = "OA por confirmar",
            objetivo = "Reconocer fracciones equivalentes y sumar fracciones con igual denominador.",
            contenidos = listOf(
                Contenido(
                    titulo = "¿Qué es una fracción?",
                    texto = "Una fracción representa una o más partes iguales de un entero. El denominador dice en cuántas " +
                            "partes iguales se dividió el entero y el numerador cuántas partes se toman. " +
                            "Dos fracciones son equivalentes si representan la misma cantidad.",
                    ejemplo = "Si una pizza se corta en 8 trozos y comes 4, comiste 4/8, que es lo mismo que 1/2.",
                    ideaClave = "Si multiplicas numerador y denominador por el mismo número, obtienes una fracción equivalente.",
                    minutos = 8,
                    visualTitulo = "Fracciones equivalentes",
                    visual = listOf("1/2" to "media pizza", "2/4" to "dos cuartos", "4/8" to "cuatro octavos")
                )
            )
        ),
        Unidad(
            asignaturaId = "3",
            etiqueta = "Unidad 1",
            titulo = "Materia y sus transformaciones",
            oa = "OA 13",
            objetivo = "Distinguir sustancias puras y mezclas, y explicar cambios de estado usando ejemplos cotidianos.",
            contenidos = listOf(
                Contenido(
                    titulo = "¿Qué es la materia?",
                    texto = "Todo lo que ocupa un lugar y tiene masa es materia. El agua, el aire, una mesa y tu cuerpo son " +
                            "materia. Puede presentarse en distintos estados y cambiar cuando recibe o pierde calor.",
                    ejemplo = "El aire de un globo ocupa espacio (lo infla) y tiene masa, por eso es materia.",
                    ideaClave = "La materia no desaparece; cambia de estado.",
                    minutos = 8,
                    audio = "2:14 min",
                    visualTitulo = "Los tres estados",
                    visual = listOf("Sólido" to "Hielo", "Líquido" to "Agua", "Gas" to "Vapor")
                ),
                Contenido(
                    titulo = "Los estados de la materia",
                    texto = "La materia se presenta como sólido, líquido o gas. El sólido tiene forma y volumen propios. " +
                            "El líquido tiene volumen propio, pero toma la forma del recipiente. El gas ocupa todo el recipiente.",
                    ejemplo = "El hielo, el agua y el vapor son el mismo material en tres estados distintos.",
                    ideaClave = "Al recibir o perder calor, la materia cambia de estado.",
                    minutos = 6
                ),
                Contenido(
                    titulo = "Sustancias puras y mezclas",
                    texto = "Una sustancia pura tiene un solo componente, como el agua destilada o el oro. " +
                            "Una mezcla une dos o más sustancias, como el jugo con trozos de fruta o el agua con sal.",
                    ejemplo = "Una ensalada es una mezcla: puedes ver y separar sus componentes.",
                    ideaClave = "En una mezcla, cada sustancia conserva sus propiedades.",
                    minutos = 7
                ),
                Contenido(
                    titulo = "La masa se conserva",
                    texto = "Cuando la materia cambia de estado, su masa no cambia. Si derrites 50 g de hielo, obtienes 50 g " +
                            "de agua. Cambia cómo está organizada, pero no la cantidad de materia.",
                    ejemplo = "Un frasco cerrado con agua pesa lo mismo antes y después de calentarse un poco.",
                    ideaClave = "Cambiar de estado no hace aparecer ni desaparecer materia.",
                    minutos = 6
                )
            )
        ),
        Unidad(
            asignaturaId = "4",
            etiqueta = "Unidad 2",
            titulo = "Chile republicano",
            oa = "OA por confirmar",
            objetivo = "Reconocer hechos y la organización política de Chile durante el siglo XIX.",
            contenidos = listOf(
                Contenido(
                    titulo = "Los inicios de la república",
                    texto = "Chile proclamó su independencia en 1818 y comenzó a organizarse como república. " +
                            "Se redactaron constituciones que organizaron el Estado y reconocieron derechos y deberes de las personas.",
                    ejemplo = "La Constitución de 1833 organizó el Estado chileno durante gran parte del siglo XIX.",
                    ideaClave = "La independencia dio inicio a la vida republicana de Chile.",
                    minutos = 8
                )
            )
        )
    )

    private fun q(tema: String, enunciado: String, correcta: Int, a: String, b: String, c: String, d: String) =
        PreguntaQuiz(tema, enunciado, listOf(a, b, c, d), correcta)

    private val preguntas: Map<String, Map<Etapa, List<PreguntaQuiz>>> = mapOf(
        // ---------------- Lenguaje ----------------
        "1" to mapOf(
            Etapa.ACTIVIDAD to listOf(
                q("Narrador", "En un cuento, ¿quién cuenta los hechos que les suceden a los personajes?", 0,
                    "El narrador", "El lector", "El editor", "El ilustrador"),
                q("Estructura del cuento", "En un cuento, ¿cómo se llama la parte donde se resuelve el conflicto?", 2,
                    "Inicio", "Desarrollo", "Desenlace", "Título")
            ),
            Etapa.DESAFIO to listOf(
                q("Conflicto", "Lee: «Pedro olvidó su paraguas y la lluvia lo sorprendió camino a la escuela. Llegó empapado y pidió una toalla.» ¿Cuál es el conflicto?", 1,
                    "Pedro llegó tarde a clases", "Pedro se quedó sin paraguas bajo la lluvia", "Pedro pidió una toalla", "La escuela estaba cerrada"),
                q("Personajes", "En el mismo texto, ¿quién es el personaje principal?", 2,
                    "La lluvia", "El paraguas", "Pedro", "La toalla")
            ),
            Etapa.PRUEBA to listOf(
                q("Inicio del cuento", "¿Qué parte del cuento presenta a los personajes y el lugar?", 1,
                    "El desenlace", "El inicio", "El título", "La moraleja"),
                q("Personajes", "¿Qué es un personaje secundario?", 2,
                    "El narrador del cuento", "El que protagoniza la historia", "El que acompaña al protagonista y tiene menos importancia", "El autor del texto"),
                q("Conflicto", "El problema que enfrentan los personajes se llama...", 0,
                    "conflicto", "rima", "estrofa", "título")
            )
        ),
        // ---------------- Matemática ----------------
        "2" to mapOf(
            Etapa.ACTIVIDAD to listOf(
                q("Fracciones equivalentes", "¿Cuál de estas fracciones es equivalente a 1/2?", 0,
                    "2/4", "1/3", "2/3", "3/5"),
                q("Suma de fracciones", "¿Cuánto es 1/4 + 2/4?", 1,
                    "3/8", "3/4", "2/4", "1/2")
            ),
            Etapa.DESAFIO to listOf(
                q("Suma de fracciones", "Ana comió 2/8 de una pizza y Luis 3/8. ¿Qué fracción comieron entre los dos?", 1,
                    "5/16", "5/8", "1/8", "6/8"),
                q("Fracciones equivalentes", "Una receta usa 1/2 taza de azúcar. ¿Cuántos cuartos de taza son equivalentes?", 2,
                    "1/4", "3/4", "2/4", "4/4")
            ),
            Etapa.PRUEBA to listOf(
                q("Fracciones equivalentes", "¿Cuál fracción es equivalente a 3/6?", 0,
                    "1/2", "1/3", "2/3", "3/12"),
                q("Suma de fracciones", "¿Cuánto es 3/10 + 4/10?", 1,
                    "7/20", "7/10", "1/10", "12/10"),
                q("Partes de una fracción", "En la fracción 5/8, ¿qué indica el denominador 8?", 2,
                    "Las partes que se pintan", "El total de pizzas", "En cuántas partes iguales se divide el entero", "El resultado")
            )
        ),
        // ---------------- Ciencias Naturales ----------------
        "3" to mapOf(
            Etapa.ACTIVIDAD to listOf(
                q("Propiedades de la materia", "¿Cuál de estos elementos es materia?", 1,
                    "La luz de una linterna", "El aire dentro de un globo", "Un sonido fuerte", "Una idea"),
                q("Estados de la materia", "El hielo es agua en estado...", 2,
                    "líquido", "gaseoso", "sólido", "ninguno de los anteriores"),
                q("Estados de la materia", "¿Qué estado de la materia toma la forma y ocupa todo el volumen del recipiente?", 2,
                    "Sólido", "Líquido", "Gas", "Ninguno"),
                q("Evaporación", "Al calentar agua en una olla, comienza a salir vapor. ¿Qué cambio de estado ocurrió?", 1,
                    "Se derrite y pasa de sólido a líquido.", "Se evapora y pasa de líquido a gas.",
                    "Se congela y pasa de líquido a sólido.", "No cambia su estado físico."),
                q("Fusión", "Un helado se derrite al sol. ¿Qué cambio de estado ocurre?", 0,
                    "Fusión: de sólido a líquido", "Evaporación: de líquido a gas",
                    "Solidificación: de líquido a sólido", "Condensación: de gas a líquido"),
                q("Solidificación", "Al poner agua en el congelador se forma hielo. Este cambio se llama...", 1,
                    "fusión", "solidificación", "evaporación", "condensación"),
                q("Condensación", "Aparecen gotitas en el exterior de un vaso con agua fría. El vapor del aire pasó a líquido. ¿Cómo se llama este cambio?", 2,
                    "Evaporación", "Fusión", "Condensación", "Solidificación"),
                q("Cambio de estado", "Cuando la materia cambia de estado...", 2,
                    "desaparece", "siempre se transforma en otra sustancia",
                    "sigue siendo la misma sustancia en otro estado", "siempre se vuelve más pesada")
            ),
            Etapa.DESAFIO to listOf(
                q("Condensación en la cocina", "Hierves agua y la tapa de la olla se llena de gotitas. ¿Qué pasó?", 0,
                    "El vapor se condensó al tocar la tapa más fría", "La tapa fabrica agua",
                    "El agua se solidificó", "El vapor desapareció"),
                q("Fusión en la cocina", "La mantequilla sobre pan caliente se ablanda hasta quedar líquida. ¿Qué cambio de estado ocurre?", 0,
                    "Fusión", "Condensación", "Solidificación", "Evaporación"),
                q("Mezclas", "Mezclas agua con sal y la sal deja de verse. ¿Qué obtuviste?", 1,
                    "Una sustancia pura", "Una mezcla", "Un gas", "Un sólido puro"),
                q("Sustancias y mezclas", "¿Cuál de estos es una mezcla?", 2,
                    "Agua destilada", "Oro puro", "Jugo con trozos de fruta", "Oxígeno puro")
            ),
            Etapa.PRUEBA to listOf(
                q("Propiedades de la materia", "Una propiedad de toda la materia es...", 0,
                    "tener masa y ocupar un lugar", "ser siempre visible", "ser siempre sólida", "tener color"),
                q("Sustancias y mezclas", "¿Cuál de estas es una sustancia pura?", 2,
                    "Agua con azúcar", "Aire", "Agua destilada", "Jugo de naranja"),
                q("Cambio de estado", "Un cubo de hielo se transforma en agua líquida. Este cambio se llama...", 1,
                    "condensación", "fusión", "evaporación", "solidificación"),
                q("Evaporación", "La ropa mojada se seca al sol porque el agua líquida pasa a gas. Este cambio se llama...", 0,
                    "evaporación", "fusión", "solidificación", "condensación"),
                q("Conservación de la masa", "Derrites un cubo de hielo de 50 g. ¿Cuál será la masa del agua líquida?", 2,
                    "Menos de 50 g", "Más de 50 g", "50 g", "0 g"),
                q("Conservación de la masa", "En un frasco cerrado, el agua hierve y pasa a vapor. La masa total del frasco...", 2,
                    "aumenta", "disminuye", "se mantiene igual", "desaparece"),
                q("Mezclas", "¿Cuál es una mezcla en la que se distinguen sus componentes a simple vista?", 1,
                    "Agua con sal disuelta", "Ensalada de frutas", "Agua pura", "Oxígeno"),
                q("Condensación", "Al enfriar el vapor de agua, este pasa a estado líquido. ¿Qué cambio es?", 2,
                    "Solidificación", "Fusión", "Condensación", "Evaporación"),
                q("Conservación de la masa", "Mezclas 100 g de arena con 200 g de agua. ¿Cuál es la masa total?", 2,
                    "100 g", "200 g", "300 g", "150 g"),
                q("Calor y cambio de estado", "¿Qué se necesita para que un sólido pase a líquido?", 1,
                    "Quitarle calor", "Entregarle calor", "Dejarlo en la sombra", "Agitarlo sin calentarlo")
            )
        ),
        // ---------------- Historia ----------------
        "4" to mapOf(
            Etapa.ACTIVIDAD to listOf(
                q("Independencia de Chile", "¿En qué año se proclamó oficialmente la Independencia de Chile?", 1,
                    "1810", "1818", "1823", "1833"),
                q("Constitución", "¿Cómo se llama el documento que organiza el Estado y reconoce los derechos de las personas?", 0,
                    "Constitución", "Calendario escolar", "Mapa político", "Censo")
            ),
            Etapa.DESAFIO to listOf(
                q("Primeros gobernantes", "¿Quién fue el primer Director Supremo de Chile?", 1,
                    "Manuel Rodríguez", "Bernardo O'Higgins", "Diego Portales", "José Miguel Carrera"),
                q("Participación ciudadana", "Votar en una elección es una forma de...", 2,
                    "pagar impuestos", "ir al colegio", "participar en las decisiones del país", "cambiar la Constitución sin ayuda de nadie")
            ),
            Etapa.PRUEBA to listOf(
                q("Constitución", "La Constitución de un país establece...", 1,
                    "el calendario escolar", "las reglas básicas de organización del Estado y los derechos de las personas",
                    "la lista de feriados", "las notas de los estudiantes"),
                q("Vida republicana", "Tras la independencia, Chile comenzó a organizarse como...", 2,
                    "una colonia", "un virreinato", "una república", "un imperio"),
                q("Poderes del Estado", "¿Cuál de estos es un poder del Estado?", 0,
                    "Poder Judicial", "Poder Artístico", "Poder Escolar", "Poder Familiar")
            )
        )
    )

    fun asignaturaDe(id: String): Asignatura =
        asignaturas.firstOrNull { it.id == id } ?: asignaturas[2]

    fun unidadDe(id: String): Unidad =
        unidades.firstOrNull { it.asignaturaId == id } ?: unidades[2]

    fun preguntasDe(id: String, etapa: Etapa): List<PreguntaQuiz> =
        preguntas[id]?.get(etapa) ?: emptyList()
}