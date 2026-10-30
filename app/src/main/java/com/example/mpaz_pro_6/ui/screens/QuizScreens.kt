package com.example.mpaz_pro_6.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mpaz_pro_6.model.Etapa
import com.example.mpaz_pro_6.model.MockRepository
import com.example.mpaz_pro_6.model.ProgresoRepository
import com.example.mpaz_pro_6.model.ResultadoEtapa
import com.example.mpaz_pro_6.ui.theme.Paleta


@Composable
fun PantallaQuiz(
    navController: NavHostController,
    asignaturaId: String,
    etapa: Etapa
) {

    val preguntas =
        remember(
            asignaturaId,
            etapa
        ) {

            MockRepository
                .preguntasDe(
                    asignaturaId,
                    etapa
                )
        }


    /*
     * La validación se calcula una sola vez
     * cuando entramos al quiz.
     *
     * Esto evita que al guardar el último
     * intento de la prueba la pantalla se
     * invalide antes de navegar al resultado.
     */
    val permitido =
        remember(
            asignaturaId,
            etapa
        ) {

            val etapaDesbloqueada =
                ProgresoRepository
                    .desbloqueada(
                        asignaturaId,
                        etapa
                    )


            val hayIntentos =

                etapa != Etapa.PRUEBA ||

                        ProgresoRepository
                            .intentosRestantes(
                                asignaturaId
                            ) > 0


            etapaDesbloqueada &&
                    hayIntentos
        }


    var indice by remember {
        mutableIntStateOf(0)
    }


    var seleccion by remember {
        mutableIntStateOf(-1)
    }


    var comprobada by remember {
        mutableStateOf(false)
    }


    var terminado by remember {
        mutableStateOf(false)
    }


    val elegidas =
        remember {
            mutableStateListOf<Int>()
        }


    /*
     * Si alguien intenta acceder directamente
     * a una etapa bloqueada mediante una ruta,
     * volvemos a la pantalla anterior.
     */
    if (
        !permitido ||
        preguntas.isEmpty()
    ) {

        LaunchedEffect(
            asignaturaId,
            etapa
        ) {

            navController
                .popBackStack()
        }


        return
    }


    val pregunta =
        preguntas[indice]


    val conRetro =
        etapa != Etapa.PRUEBA


    val mostrarCorreccion =
        conRetro &&
                comprobada


    val esUltima =
        indice ==
                preguntas.size - 1


    val avance =
        (
                indice +
                        if (comprobada) {
                            1
                        } else {
                            0
                        }
                ).toFloat() /
                preguntas.size


    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Paleta.Fondo
                )
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(
                    horizontal = 20.dp
                )
    ) {

        // -------------------------
        // CABECERA
        // -------------------------

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp
                    ),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {

                    navController
                        .popBackStack()
                }
            ) {

                Icon(
                    imageVector =
                        Icons.Default
                            .ArrowBack,

                    contentDescription =
                        "Volver",

                    tint =
                        Paleta.Texto
                )
            }


            Column(
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text =
                        "Pregunta ${indice + 1} de ${preguntas.size}",

                    fontWeight =
                        FontWeight.Bold,

                    fontSize =
                        15.sp,

                    color =
                        Paleta.Texto
                )


                Text(
                    text =
                        etapa.titulo,

                    fontSize =
                        12.sp,

                    color =
                        Paleta.TextoSuave
                )
            }


            IconButton(
                onClick = {

                    navController
                        .popBackStack()
                }
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Close,

                    contentDescription =
                        "Salir",

                    tint =
                        Paleta.Texto
                )
            }
        }


        // -------------------------
        // PROGRESO
        // -------------------------

        LinearProgressIndicator(
            progress = {
                avance
            },

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp
                    ),

            color =
                Paleta.Azul,

            trackColor =
                Paleta.Borde
        )


        // -------------------------
        // PREGUNTA
        // -------------------------

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(
                        rememberScrollState()
                    )
        ) {

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    CardDefaults
                        .cardColors(
                            containerColor =
                                Paleta.AzulClaro
                        ),

                shape =
                    RoundedCornerShape(
                        12.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(
                            14.dp
                        )
                ) {

                    Text(
                        text =
                            "Instrucción",

                        fontWeight =
                            FontWeight.Bold,

                        fontSize =
                            12.sp,

                        color =
                            Paleta.AzulOscuro
                    )


                    Text(
                        text =
                            "Lee con atención y marca una sola alternativa.",

                        fontSize =
                            13.sp,

                        color =
                            Paleta.Texto
                    )
                }
            }


            Spacer(
                Modifier.height(
                    16.dp
                )
            )


            Text(
                text =
                    pregunta.enunciado,

                fontSize =
                    19.sp,

                lineHeight =
                    26.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    Paleta.Texto
            )


            Spacer(
                Modifier.height(
                    16.dp
                )
            )


            // -------------------------
            // ALTERNATIVAS
            // -------------------------

            pregunta
                .opciones
                .forEachIndexed {
                        index,
                        opcion ->


                    val seleccionada =
                        index ==
                                seleccion


                    val esLaCorrecta =
                        mostrarCorreccion &&
                                index ==
                                pregunta.correcta


                    val esErrada =
                        mostrarCorreccion &&
                                seleccionada &&
                                index !=
                                pregunta.correcta


                    val borde =

                        when {

                            esLaCorrecta ->
                                Paleta.Verde

                            esErrada ->
                                Paleta.Rojo

                            seleccionada ->
                                Paleta.Azul

                            else ->
                                Color.Transparent
                        }


                    val fondo =

                        when {

                            esLaCorrecta ->
                                Paleta.VerdeClaro

                            esErrada ->
                                Paleta.RojoClaro

                            seleccionada ->
                                Paleta.AzulClaro

                            else ->
                                Color.White
                        }


                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical =
                                        5.dp
                                )
                                .clickable(
                                    enabled =
                                        !mostrarCorreccion
                                ) {

                                    seleccion =
                                        index
                                }
                                .border(
                                    2.dp,
                                    borde,
                                    RoundedCornerShape(
                                        12.dp
                                    )
                                ),

                        colors =
                            CardDefaults
                                .cardColors(
                                    containerColor =
                                        fondo
                                ),

                        shape =
                            RoundedCornerShape(
                                12.dp
                            )
                    ) {

                        Row(
                            modifier =
                                Modifier.padding(
                                    16.dp
                                ),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Text(
                                text =
                                    "${'A' + index}.",

                                fontWeight =
                                    FontWeight.Bold,

                                color =

                                    if (
                                        seleccionada
                                    ) {

                                        Paleta.Azul

                                    } else {

                                        Paleta.TextoSuave
                                    }
                            )


                            Spacer(
                                Modifier.width(
                                    12.dp
                                )
                            )


                            Text(
                                text =
                                    opcion,

                                modifier =
                                    Modifier.weight(
                                        1f
                                    ),

                                fontSize =
                                    14.sp,

                                color =
                                    Paleta.Texto
                            )


                            RadioButton(
                                selected =
                                    seleccionada,

                                onClick =
                                    null,

                                colors =
                                    RadioButtonDefaults
                                        .colors(

                                            selectedColor =
                                                Paleta.Azul,

                                            unselectedColor =
                                                Paleta.TextoSuave
                                        )
                            )
                        }
                    }
                }


            // -------------------------
            // RETROALIMENTACIÓN ANIMADA
            // -------------------------

            AnimatedVisibility(
                visible =
                    mostrarCorreccion,

                enter =
                    fadeIn(
                        animationSpec =
                            tween(
                                220
                            )
                    ) +

                            expandVertically(
                                animationSpec =
                                    tween(
                                        280
                                    )
                            ),

                exit =
                    fadeOut(
                        animationSpec =
                            tween(
                                150
                            )
                    ) +

                            shrinkVertically(
                                animationSpec =
                                    tween(
                                        200
                                    )
                            )
            ) {

                val acerto =
                    seleccion ==
                            pregunta.correcta


                Column {

                    Spacer(
                        Modifier.height(
                            10.dp
                        )
                    )


                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),

                        colors =
                            CardDefaults
                                .cardColors(

                                    containerColor =

                                        if (
                                            acerto
                                        ) {

                                            Paleta.VerdeClaro

                                        } else {

                                            Paleta.RojoClaro
                                        }
                                ),

                        shape =
                            RoundedCornerShape(
                                12.dp
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(
                                    14.dp
                                )
                        ) {

                            Text(
                                text =

                                    if (
                                        acerto
                                    ) {

                                        "¡Correcto!"

                                    } else {

                                        "Casi lo logras"
                                    },

                                fontWeight =
                                    FontWeight.Bold,

                                fontSize =
                                    15.sp,

                                color =

                                    if (
                                        acerto
                                    ) {

                                        Paleta.VerdeOscuro

                                    } else {

                                        Paleta.RojoOscuro
                                    }
                            )


                            if (
                                !acerto
                            ) {

                                Text(
                                    text =
                                        "La respuesta correcta es: ${pregunta.opciones[pregunta.correcta]}",

                                    fontSize =
                                        13.sp,

                                    color =
                                        Paleta.Texto
                                )
                            }
                        }
                    }
                }
            }


            Spacer(
                Modifier.height(
                    12.dp
                )
            )
        }


        // -------------------------
        // BOTÓN INFERIOR
        // -------------------------

        val textoBoton =

            when {

                conRetro &&
                        !comprobada ->

                    "Comprobar respuesta"


                esUltima &&
                        etapa ==
                        Etapa.PRUEBA ->

                    "Finalizar prueba"


                esUltima ->

                    "Ver resultado"


                else ->

                    "Siguiente pregunta"
            }


        Button(
            onClick = {

                if (
                    conRetro &&
                    !comprobada
                ) {

                    comprobada =
                        true

                } else {

                    elegidas.add(
                        seleccion
                    )


                    if (
                        esUltima
                    ) {

                        if (
                            !terminado
                        ) {

                            terminado =
                                true


                            ProgresoRepository
                                .guardarResultado(

                                    asignaturaId,

                                    etapa,

                                    ResultadoEtapa(
                                        elegidas =
                                            elegidas
                                                .toList(),

                                        preguntas =
                                            preguntas
                                    )
                                )


                            navController
                                .navigate(

                                    "resultado/$asignaturaId/${etapa.ruta}"

                                ) {

                                    popUpTo(
                                        "quiz/{asignaturaId}/{etapa}"
                                    ) {

                                        inclusive =
                                            true
                                    }
                                }
                        }

                    } else {

                        indice++


                        seleccion =
                            -1


                        comprobada =
                            false
                    }
                }
            },

            enabled =
                seleccion >= 0 &&
                        !terminado,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(
                        50.dp
                    ),

            colors =
                ButtonDefaults
                    .buttonColors(

                        containerColor =
                            Paleta.Azul,

                        contentColor =
                            Color.White
                    ),

            shape =
                RoundedCornerShape(
                    12.dp
                )
        ) {

            Text(
                text =
                    textoBoton
            )
        }


        Text(
            text =

                if (
                    conRetro
                ) {

                    "Puedes cambiar tu respuesta antes de comprobar."

                } else {

                    "Verás tus respuestas correctas e incorrectas al terminar."
                },

            fontSize =
                11.sp,

            color =
                Paleta.TextoSuave,

            textAlign =
                TextAlign.Center,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical =
                            8.dp
                    )
        )
    }
}