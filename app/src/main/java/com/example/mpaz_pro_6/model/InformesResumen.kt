package com.example.mpaz_pro_6.model

// Estado de cada informe tal como se muestra en la lista del docente.
enum class EstadoListaInforme(val texto: String) {
    SIN_RESULTADOS("Sin resultados"),
    PENDIENTE("Pendiente de validar"),
    VALIDADO("Validado")
}

// Resumen de informes por asignatura. Solo lee los repositorios existentes.
object InformesResumen {

    fun tieneResultados(asignaturaId: String): Boolean {
        val progreso = ProgresoRepository.de(asignaturaId)
        return progreso.actividad != null ||
                progreso.desafio != null ||
                progreso.intentosPrueba.isNotEmpty()
    }

    fun estado(asignaturaId: String): EstadoListaInforme {
        if (!tieneResultados(asignaturaId)) {
            return EstadoListaInforme.SIN_RESULTADOS
        }
        return when (GestionAcademicaRepository.estadoInforme(asignaturaId)) {
            EstadoInformePedagogico.PENDIENTE -> EstadoListaInforme.PENDIENTE
            EstadoInformePedagogico.VALIDADO -> EstadoListaInforme.VALIDADO
        }
    }

    // Promedio de actividad, desafío y último intento de la prueba (null si no hay resultados).
    fun rendimiento(asignaturaId: String): Int? {
        val progreso = ProgresoRepository.de(asignaturaId)
        val resultados = listOfNotNull(
            progreso.actividad,
            progreso.desafio,
            progreso.intentosPrueba.lastOrNull()
        )
        if (resultados.isEmpty()) return null
        return resultados.map { it.porcentaje }.average().toInt()
    }

    fun pendientes(): Int =
        MockRepository.asignaturas.count { estado(it.id) == EstadoListaInforme.PENDIENTE }

    fun validados(): Int =
        MockRepository.asignaturas.count { estado(it.id) == EstadoListaInforme.VALIDADO }
}