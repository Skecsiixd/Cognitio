package com.example.cognitio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
fun PVASAT_Start(num: Int) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Kezdoszam: $num", fontSize = 60.sp)
    }
}

@Composable
fun PVASAT_End(num: Int, clickFun: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Vegso Pontszam: $num",
            fontSize = 40.sp
        )
        Button(onClick = { clickFun() }, modifier = Modifier.size(200.dp)) {
            Text("SHOW", fontSize = 25.sp)
        }
    }
}


@Composable
fun PVASAT(
    modifier: Modifier = Modifier.Companion,
    onFinished: (finalScore: Int) -> Unit,
    onExit: () -> Unit
) {
    var kivalasztott by remember { mutableStateOf<Int?>(null) }
    var current by remember { mutableIntStateOf(Random.nextInt(1, 10)) }
    var prev by remember { mutableIntStateOf(0) }
    var potentialcur: Int
    var showGame by remember { mutableStateOf(false) }
    var showEnd by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var didchose by remember { mutableStateOf(false) }
    if (!showEnd) {
        if (!showGame) {
            PVASAT_Start(num = current)
            LaunchedEffect(Unit) {
                delay(3000.milliseconds)
                showGame = true
            }
        } else {
            LaunchedEffect(Unit) {
                repeat(10) {
                    didchose = false
                    prev = current
                    potentialcur = Random.nextInt(1, 10)
                    while (potentialcur == current) {
                        potentialcur = Random.nextInt(1, 10)
                    }
                    current = potentialcur
                    kivalasztott = null
                    delay(3000.milliseconds)
                }
                showEnd = true
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(current.toString(), fontSize = 50.sp)
                //Text(prev.toString(), fontSize = 50.sp)
                Spacer(modifier = Modifier.height(30.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items((3..17).toList()) { szam ->

                        val aktiv = kivalasztott == szam

                        Button(

                            onClick = {
                                if (!didchose) {
                                    kivalasztott = szam
                                    if (kivalasztott == prev + current) {
                                        score++
                                    }
                                    didchose = true
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.3f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (aktiv) {
                                    if (kivalasztott == prev + current) {
                                        Color.Green

                                    } else {
                                        Color.Red
                                    }
                                } else
                                    MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = if (aktiv)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(szam.toString(), fontSize = 30.sp)
                        }
                    }
                }
            }
        }
    } else {
        PVASAT_End(num = score, clickFun = onExit)
        onFinished(score)
    }
}