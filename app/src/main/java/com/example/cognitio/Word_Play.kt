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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun Word_Play() {
    var selectedWords by rememberSaveable { mutableStateOf(words.shuffled().take(6)) }

    var test by remember { mutableStateOf(false) }
    if (!test) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            selectedWords.forEach {
                Text(it, fontSize = 30.sp)
            }

            Button(onClick = { test = true }) { }

        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Word_Play_Request(selectedWords)
        }
    }
}

@Composable
fun Word_Play_Request(selected: List<String>) {
    var text by rememberSaveable { mutableStateOf("") }
    var correctWords by rememberSaveable { mutableStateOf(setOf<String>()) }

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
}