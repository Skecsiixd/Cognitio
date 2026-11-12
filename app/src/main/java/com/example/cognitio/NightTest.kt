package com.example.cognitio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

enum class GameDestination(
    val label: String,

    ) {
    PVASAT("pvasat"),
    PVT("pvt"),
    WORDPLAY("wordplay"),
    WORDREQUEST("wordrequest")
}

@Composable
fun NightTest(modifier: Modifier = Modifier.Companion, onFinished: () -> Unit) {


    var selectedWords by rememberSaveable { mutableStateOf(words.shuffled().take(6)) }

    var currentGame by rememberSaveable { mutableStateOf(GameDestination.WORDPLAY) }

    LaunchedEffect(currentGame) {
        if (currentGame == GameDestination.WORDPLAY) {
            delay(10_000) // 10 másodperc
            currentGame = GameDestination.PVT
        }
    }

    when (currentGame) {
        GameDestination.WORDPLAY -> Word_Play(selectedWords)
        GameDestination.PVT -> PVT { currentGame = GameDestination.PVASAT }
        GameDestination.PVASAT -> PVASAT(modifier) { currentGame = GameDestination.WORDREQUEST }
        GameDestination.WORDREQUEST -> Word_Play_Request(selectedWords, onFinished)
    }
}