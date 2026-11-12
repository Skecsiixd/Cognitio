package com.example.cognitio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp


@Composable
fun Word_Play(selectedWords: List<String>) {


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectedWords.forEach {
            Text(it, fontSize = 30.sp)
        }


    }
}


@Composable
fun Word_Play_Request(
    selected: List<String>,
    onFinished: (finalScore: Int) -> Unit,
    onExit: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    var correctWords by rememberSaveable { mutableStateOf(setOf<String>()) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Írd be a szót") },
            singleLine = true,
            keyboardActions = KeyboardActions(
            ),
            modifier = Modifier.fillMaxWidth()
        )

        val match = selected.find { it.normalizeWord() == text.normalizeWord() }
        if (match != null) {
            correctWords = correctWords + match
            text = ""
        }
        Text(correctWords.joinToString(", "))

        Text(correctWords.size.toString(), fontSize = 50.sp)
        onFinished(correctWords.size)
        Button(onClick = onExit) {
            Text("EXIT")
        }
    }
}