package com.example.cognitio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class GameDestination {
    PVASAT,
    PVT,
    WORDPLAY,
    WORDREQUEST,
    ENDSCREEN
}

@Composable
fun NightTest(modifier: Modifier = Modifier, onFinished: () -> Unit) {


    var selectedWords by rememberSaveable { mutableStateOf(words.shuffled().take(6)) }

    var currentGame by rememberSaveable { mutableStateOf(GameDestination.WORDPLAY) }

    var pvtscore by rememberSaveable { mutableLongStateOf(0) }
    var pvasatscore by rememberSaveable { mutableIntStateOf(0) }
    var wordscore by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(currentGame) {
        if (currentGame == GameDestination.WORDPLAY) {
            delay(10_000) // 10 másodperc
            currentGame = GameDestination.PVT
        }
    }

    when (currentGame) {


        GameDestination.WORDPLAY -> Word_Play(selectedWords)
        GameDestination.PVT -> PVT({ pvtscore = it }) { currentGame = GameDestination.PVASAT }
        GameDestination.PVASAT -> PVASAT(modifier, { pvasatscore = it }) {
            currentGame = GameDestination.WORDREQUEST
        }

        GameDestination.WORDREQUEST -> Word_Play_Request(selectedWords, { wordscore = it }) {
            currentGame =
                GameDestination.ENDSCREEN
        }

        GameDestination.ENDSCREEN -> EndScreen(
            modifier,
            pvtscore,
            pvasatscore,
            wordscore,
            onFinished
        )
    }
}


@Composable
fun EndScreen(
    modifier: Modifier,
    PVT_Score: Long,
    PVASAT_Score: Int,
    Correct_Words: Int,
    onExit: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("PVT Reakcioido: $PVT_Score ms", fontSize = 30.sp)
        Text("PVASAT Pontszam: $PVASAT_Score", fontSize = 30.sp)
        Text("Szavak: $Correct_Words", fontSize = 30.sp)
        Button(onClick = onExit) {
            Text("EXIT")
        }
    }
}