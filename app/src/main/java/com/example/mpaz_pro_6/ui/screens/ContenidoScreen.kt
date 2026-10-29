package com.example.mpaz_pro_6.ui.screens

import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.Etapa
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.ui.theme.Paleta
import java.util.Locale


@Composable
fun PantallaContenido(
    navController: NavHostController,
    asignaturaId: String,
    indice: Int
) {

    val context = LocalContext.current

    val asignatura =
        MockRepository.asignaturaDe(asignaturaId)

    val unidad =
        MockRepository.unidadDe(asignaturaId)

    val contenido =
        unidad.contenidos.getOrNull(indice)


    // -------------------------
    // ESTADOS DEL TEXT TO SPEECH
    // -------------------------

    var textToSpeech by remember {
        mutableStateOf<TextToSpeech?>(null)
    }

    var ttsDisponible by remember {
        mutableStateOf(false)
    }

    var sonando by remember {
        mutableStateOf(false)
    }

    var errorTts by remember {
        mutableStateOf<String?>(null)
    }


    val mainHandler = remember {
        Handler(Looper.getMainLooper())
    }


    // -------------------------
    // INICIALIZAR TEXT TO SPEECH
    // -------------------------

    DisposableEffect(Unit) {

        val tts =
            TextToSpeech(context) { status ->

                if (status == TextToSpeech.SUCCESS) {

                    val motor = textToSpeech

                    if (motor != null) {

                        // Primero intentamos español de Chile.
                        var resultadoIdioma =
                            motor.setLanguage(
                                Locale("es", "CL")
                            )


                        // Si español de Chile no está disponible,
                        // intentamos español general.
                        if (
                            resultadoIdioma ==
                            TextToSpeech.LANG_MISSING_DATA ||
                            resultadoIdioma ==
                            TextToSpeech.LANG_NOT_SUPPORTED
                        ) {

                            resultadoIdioma =
                                motor.setLanguage(
                                    Locale("es")
                                )
                        }


                        if (
                            resultadoIdioma ==
                            TextToSpeech.LANG_MISSING_DATA ||
                            resultadoIdioma ==
                            TextToSpeech.LANG_NOT_SUPPORTED
                        ) {

                            ttsDisponible = false

                            errorTts =
                                "El dispositivo no tiene disponible una voz en español."

                        } else {

                            // Velocidad de lectura.
                            motor.setSpeechRate(0.9f)

                            ttsDisponible = true
                            errorTts = null
                        }
                    }

                } else {

                    ttsDisponible = false

                    errorTts =
                        "No se pudo iniciar el lector de voz."
                }
            }


        textToSpeech = tts


        // Escuchamos cuándo empieza y termina
        // la lectura.
        tts.setOnUtteranceProgressListener(

            object : UtteranceProgressListener() {

                override fun onStart(
                    utteranceId: String?
                ) {

                    mainHandler.post {
                        sonando = true
                    }
                }


                override fun onDone(
                    utteranceId: String?
                ) {

                    mainHandler.post {
                        sonando = false
                    }
                }


                @Deprecated("Método antiguo requerido por Android")
                override fun onError(
                    utteranceId: String?
                ) {

                    mainHandler.post {

                        sonando = false

                        errorTts =
                            "Ocurrió un problema al reproducir la explicación."
                    }
                }


                override fun onError(
                    utteranceId: String?,
                    errorCode: Int
                ) {

                    mainHandler.post {

                        sonando = false

                        errorTts =
                            "Ocurrió un problema al reproducir la explicación."
                    }
                }
            }
        )


        // Cuando salimos de esta pantalla,
        // detenemos y liberamos el motor TTS.
        onDispose {

            tts.stop()
            tts.shutdown()

            textToSpeech = null
        }
    }


    if (contenido == null) {

        LaunchedEffect(Unit) {
            navController.popBackStack()
        }

        return
    }


    val total =
        unidad.contenidos.size

    val esUltimo =
        indice == total - 1

    val color =
        Color(asignatura.colorHex)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Paleta.Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {

        // -------------------------
        // ENCABEZADO
        // -------------------------

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {

                    textToSpeech?.stop()
                    sonando = false

                    navController.popBackStack()
                }
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ArrowBack,

                    contentDescription =
                        "Volver",

                    tint =
                        Paleta.Texto
                )
            }


            Column {

                Text(
                    text =
                        asignatura.nombre,

                    fontWeight =
                        FontWeight.Bold,

                    fontSize = 16.sp,

                    color =
                        Paleta.Texto
                )


                Text(
                    text =
                        "Contenido ${indice + 1} de $total",

                    fontSize = 12.sp,

                    color =
                        Paleta.TextoSuave
                )
            }
        }


        // -------------------------
        // CONTENIDO CON SCROLL
        // -------------------------

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(top = 12.dp)
        ) {

            Surface(
                color =
                    Paleta.VerdeClaro,

                shape =
                    RoundedCornerShape(20.dp)
            ) {

                Text(
                    text =
                        "Lectura · ${contenido.minutos} min",

                    color =
                        Paleta.VerdeOscuro,

                    fontSize = 12.sp,

                    fontWeight =
                        FontWeight.Bold,

                    modifier =
                        Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        )
                )
            }


            Spacer(
                Modifier.height(14.dp)
            )


            Text(
                text =
                    contenido.titulo,

                fontSize = 24.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Paleta.Texto
            )


            Spacer(
                Modifier.height(8.dp)
            )


            Text(
                text =
                    contenido.texto,

                fontSize = 15.sp,

                lineHeight = 22.sp,

                color =
                    Paleta.TextoSuave
            )


            // -------------------------
            // RECURSO VISUAL
            // -------------------------

            if (
                contenido.visual.isNotEmpty()
            ) {

                Spacer(
                    Modifier.height(16.dp)
                )


                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                color.copy(
                                    alpha = 0.12f
                                )
                        ),

                    shape =
                        RoundedCornerShape(16.dp)
                ) {

                    Column(
                        modifier =
                            Modifier.padding(16.dp)
                    ) {

                        Text(
                            text =
                                contenido.visualTitulo
                                    ?: "En resumen",

                            fontWeight =
                                FontWeight.Bold,

                            fontSize = 14.sp,

                            color =
                                Paleta.Texto
                        )


                        Spacer(
                            Modifier.height(10.dp)
                        )


                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.spacedBy(
                                    10.dp
                                )
                        ) {

                            contenido.visual
                                .forEach {
                                        (titulo, detalle) ->

                                    Card(
                                        modifier =
                                            Modifier.weight(1f),

                                        colors =
                                            CardDefaults.cardColors(
                                                containerColor =
                                                    Color.White
                                            ),

                                        shape =
                                            RoundedCornerShape(
                                                12.dp
                                            )
                                    ) {

                                        Column(
                                            modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(12.dp),

                                            horizontalAlignment =
                                                Alignment.CenterHorizontally
                                        ) {

                                            Text(
                                                text =
                                                    titulo,

                                                fontWeight =
                                                    FontWeight.Bold,

                                                fontSize =
                                                    15.sp,

                                                color =
                                                    Paleta.Texto,

                                                textAlign =
                                                    TextAlign.Center
                                            )


                                            Text(
                                                text =
                                                    detalle,

                                                fontSize =
                                                    12.sp,

                                                color =
                                                    Paleta.TextoSuave,

                                                textAlign =
                                                    TextAlign.Center
                                            )
                                        }
                                    }
                                }
                        }
                    }
                }
            }


            // -------------------------
            // TEXT TO SPEECH
            // -------------------------

            contenido.audio?.let {

                Spacer(
                    Modifier.height(12.dp)
                )


                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.White
                        ),

                    shape =
                        RoundedCornerShape(16.dp)
                ) {

                    Row(
                        modifier =
                            Modifier.padding(14.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        FilledIconButton(

                            enabled =
                                ttsDisponible,

                            onClick = {

                                if (sonando) {

                                    // DETENER
                                    textToSpeech?.stop()

                                    sonando = false

                                } else {

                                    // TEXTO QUE VA A LEER EL CELULAR.
                                    val textoALeer =
                                        "${contenido.titulo}. " +
                                                "${contenido.texto}. " +
                                                "Ejemplo. ${contenido.ejemplo}. " +
                                                "Idea clave. ${contenido.ideaClave}"


                                    val resultado =
                                        textToSpeech?.speak(

                                            textoALeer,

                                            TextToSpeech.QUEUE_FLUSH,

                                            null,

                                            "contenido_${asignaturaId}_$indice"
                                        )


                                    if (
                                        resultado ==
                                        TextToSpeech.ERROR
                                    ) {

                                        errorTts =
                                            "No se pudo iniciar la lectura."
                                    }
                                }
                            },

                            colors =
                                IconButtonDefaults
                                    .filledIconButtonColors(

                                        containerColor =
                                            Paleta.Azul,

                                        contentColor =
                                            Color.White
                                    )
                        ) {

                            Icon(
                                imageVector =
                                    if (sonando) {

                                        Icons.Default.Pause

                                    } else {

                                        Icons.Default.PlayArrow
                                    },

                                contentDescription =
                                    if (sonando) {

                                        "Detener explicación"

                                    } else {

                                        "Escuchar explicación"
                                    }
                            )
                        }


                        Spacer(
                            Modifier.width(12.dp)
                        )


                        Column {

                            Text(
                                text =
                                    "Escuchar explicación",

                                fontWeight =
                                    FontWeight.Bold,

                                fontSize = 14.sp,

                                color =
                                    Paleta.Texto
                            )


                            Text(
                                text =
                                    when {

                                        sonando ->
                                            "Leyendo contenido en voz alta…"

                                        !ttsDisponible &&
                                                errorTts == null ->
                                            "Preparando lector de voz…"

                                        else ->
                                            "Lectura de apoyo"
                                    },

                                fontSize = 12.sp,

                                color =
                                    Paleta.TextoSuave
                            )
                        }
                    }
                }


                // Mensaje si el motor TTS falla.
                errorTts?.let { mensaje ->

                    Spacer(
                        Modifier.height(6.dp)
                    )

                    Text(
                        text = mensaje,

                        color =
                            MaterialTheme
                                .colorScheme
                                .error,

                        fontSize = 12.sp
                    )
                }
            }


            // -------------------------
            // EJEMPLO
            // -------------------------

            Spacer(
                Modifier.height(12.dp)
            )


            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.White
                    ),

                shape =
                    RoundedCornerShape(16.dp)
            ) {

                Column(
                    modifier =
                        Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Ejemplo",

                        fontWeight =
                            FontWeight.Bold,

                        fontSize = 12.sp,

                        color =
                            Paleta.Azul
                    )


                    Spacer(
                        Modifier.height(4.dp)
                    )


                    Text(
                        text =
                            contenido.ejemplo,

                        fontSize = 14.sp,

                        color =
                            Paleta.Texto
                    )
                }
            }


            // -------------------------
            // IDEA CLAVE
            // -------------------------

            Spacer(
                Modifier.height(12.dp)
            )


            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Paleta.AmbarClaro
                    ),

                shape =
                    RoundedCornerShape(16.dp)
            ) {

                Row(
                    modifier =
                        Modifier.padding(14.dp),

                    verticalAlignment =
                        Alignment.Top
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Info,

                        contentDescription =
                            null,

                        tint =
                            Paleta.Ambar
                    )


                    Spacer(
                        Modifier.width(10.dp)
                    )


                    Text(
                        text =
                            "Idea clave: ${contenido.ideaClave}",

                        fontSize = 14.sp,

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            Paleta.AmbarOscuro
                    )
                }
            }


            Spacer(
                Modifier.height(12.dp)
            )
        }


        // -------------------------
        // SIGUIENTE CONTENIDO
        // -------------------------

        Button(
            onClick = {

                // Detenemos la lectura antes de avanzar.
                textToSpeech?.stop()
                sonando = false


                ProgresoRepository
                    .marcarContenido(
                        asignaturaId,
                        indice
                    )


                val destino =

                    if (esUltimo) {

                        "quiz/$asignaturaId/${Etapa.ACTIVIDAD.ruta}"

                    } else {

                        "contenido/$asignaturaId/${indice + 1}"
                    }


                navController.navigate(
                    destino
                ) {

                    popUpTo(
                        "contenido/{asignaturaId}/{indice}"
                    ) {

                        inclusive = true
                    }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        Paleta.Azul,

                    contentColor =
                        Color.White
                ),

            shape =
                RoundedCornerShape(12.dp)
        ) {

            Text(

                if (esUltimo) {

                    "Ir a la actividad"

                } else {

                    "Siguiente contenido"
                }
            )
        }


        Spacer(
            Modifier.height(12.dp)
        )
    }
}