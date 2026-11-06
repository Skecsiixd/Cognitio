package com.example.cognitio

import android.os.Bundle
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults.colors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cognitio.ui.theme.CognitioTheme
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import java.text.Normalizer

private val ACCENT_REGEX = "\\p{Mn}+".toRegex()

fun String.normalizeWord(): String =
    Normalizer.normalize(this.trim().lowercase(), Normalizer.Form.NFD)
        .replace(ACCENT_REGEX, "")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CognitioTheme {
                CognitioApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun CognitioApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val backgroundColor = Color(0xFFF7F1E5)
    NavigationSuiteScaffold(
        containerColor = backgroundColor,
        navigationSuiteColors = colors(
            backgroundColor
        ),
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = backgroundColor
        ) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen(
                    Modifier
                        .padding(30.dp)
                        .border(width = 2.dp, color = Color.Blue)
                        .padding(5.dp)
                )

                AppDestinations.RESULTS -> ResultsScreen(Modifier.padding(innerPadding))
                AppDestinations.SETTINGS -> SettingsScreen(Modifier.padding(innerPadding))

            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Főoldal", R.drawable.ic_home),
    RESULTS("Eredmények", R.drawable.ic_favorite),
    SETTINGS("Beállítások", R.drawable.ic_account_box),

}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var showPvasat by remember { mutableStateOf(false) }
    if (showPvasat) {
        PVASAT(modifier = modifier, onExit = { showPvasat = false })
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Ez a Főoldal", fontSize = 50.sp)

            Button(onClick = { showPvasat = true }, modifier = Modifier.size(200.dp)) {
                Text("SHOW", fontSize = 25.sp)
            }


        }
    }
}

@Composable
fun ResultsScreen(modifier: Modifier = Modifier) {
    var showPVT by remember { mutableStateOf(false) }
    if (showPVT) {
        PVT { showPVT = false }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Ez az eredmeny oldal xd", fontSize = 30.sp)

            Button(onClick = { showPVT = true }, modifier = Modifier.size(200.dp)) {
                Text("SHOW", fontSize = 25.sp)
            }


        }
    }

}


@Composable
fun Word_Play() {
    val words: List<String> = listOf(
        "kutya",
        "macska",
        "medve",
        "nyúl",
        "róka",
        "farkas",
        "disznó",
        "liba",
        "béka",
        "szarvas",
        "kenyér",
        "alma",
        "sajt",
        "tojás",
        "körte",
        "répa",
        "lekvár",
        "tészta",
        "paprika",
        "uborka",
        "asztal",
        "szék",
        "ablak",
        "lámpa",
        "tükör",
        "kulcs",
        "függöny",
        "szőnyeg",
        "polc",
        "párna",
        "kabát",
        "cipő",
        "sapka",
        "nadrág",
        "zokni",
        "kesztyű",
        "szoknya",
        "ruha",
        "csizma",
        "pulóver",
        "kalapács",
        "fűrész",
        "kapa",
        "olló",
        "létra",
        "vödör",
        "lapát",
        "seprű",
        "csákány",
        "reszelő",
        "virág",
        "fenyő",
        "tölgy",
        "rózsa",
        "nyárfa",
        "bokor",
        "tulipán",
        "moha",
        "kaktusz",
        "gyökér",
        "templom",
        "torony",
        "kastély",
        "iskola",
        "piac",
        "pékség",
        "sziget",
        "kert",
        "pince",
        "hegy",
        "autó",
        "busz",
        "vonat",
        "hajó",
        "repülő",
        "bicikli",
        "traktor",
        "villamos",
        "csónak",
        "kamion",
        "ceruza",
        "könyv",
        "füzet",
        "radír",
        "papír",
        "tábla",
        "táska",
        "naptár",
        "vonalzó",
        "tinta",
        "zongora",
        "gitár",
        "hegedű",
        "trombita",
        "fuvola",
        "tuba",
        "klarinét",
        "csengő",
        "hárfa",
        "cimbalom",
    )
    var selectedWords by rememberSaveable { mutableStateOf(words.shuffled().take(6)) }
    var text by rememberSaveable { mutableStateOf("") }
    var correct by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectedWords.forEach {
            Text(it, fontSize = 30.sp)
        }
        Button(
            onClick = {
                correct = selectedWords.any { it.normalizeWord() == text.normalizeWord() }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (correct) {
                    Color.Green
                } else {
                    Color.Red
                }
            )
        ) {
            Text("check")
        }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Írd be a szót") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

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
fun PVT(onExit: () -> Unit) {
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
    }
}


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
fun PVASAT(modifier: Modifier = Modifier, onExit: () -> Unit) {
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
    }
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Word_Play()
}

/*@Preview(showBackground = true)
@Composable
*/