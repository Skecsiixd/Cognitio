package com.example.cognitio

import android.os.SystemClock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun PVT_End(score: Long, fail: Boolean, clickFun: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!fail) {
            Text(
                "Vegso Pontszam: $score",
                fontSize = 40.sp
            )
            Button(onClick = { clickFun() }, modifier = Modifier.size(200.dp)) {
                Text("SHOW", fontSize = 25.sp)
            }
        } else {
            Text(
                "YOU FAILED",
                fontSize = 40.sp
            )
            Button(onClick = { clickFun() }, modifier = Modifier.size(200.dp)) {
                Text("SHOW", fontSize = 25.sp)
            }
        }
    }
}

@Composable
fun PVT(onFinished: (finalScore: Long) -> Unit, onExit: () -> Unit) {
    var showTest by remember { mutableStateOf(false) }
    var greenReflex by remember { mutableStateOf(false) }
    var greentime by remember { mutableLongStateOf(0) }
    var presstime by remember { mutableLongStateOf(0) }
    var finaltime by remember { mutableLongStateOf(0) }
    var showEnd by remember { mutableStateOf(false) }
    var failed by remember { mutableStateOf(false) }
    if (!showEnd) {
        if (!showTest) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Keszen allsz a REAKCIOTESZTRE?", fontSize = 20.sp
                )
                Button(onClick = { showTest = true }, modifier = Modifier.size(200.dp)) {
                    Text("SHOW", fontSize = 25.sp)
                }
            }
        } else {
            LaunchedEffect(Unit) {
                val time = Random.nextInt(1000, 4500)
                delay(time.milliseconds)
                greenReflex = true
                greentime = SystemClock.elapsedRealtime()
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        if (greenReflex) {
                            showEnd = true
                            greenReflex = false
                            presstime = SystemClock.elapsedRealtime()
                            finaltime = presstime - greentime
                        } else {
                            showEnd = true
                            failed = true
                        }
                    },
                    modifier = Modifier.size(200.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (greenReflex) {
                            Color.Green
                        } else {
                            Color.Red
                        }

                    )
                ) {

                }
            }
        }
    } else {
        PVT_End(finaltime, failed, onExit)
        onFinished(finaltime)
    }
}