package com.example.cognitio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults.colors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
        Scaffold(modifier = Modifier.fillMaxSize(), containerColor = backgroundColor) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen(Modifier.padding(30.dp).border(width = 2.dp, color = Color.Blue).padding(5.dp))
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
    var showHello by remember { mutableStateOf(false) }
    if (showHello) {
        PVASAT(modifier = modifier)
    }
    else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Ez a Főoldal", fontSize = 50.sp)

            Button(onClick = { showHello = true }, modifier = Modifier.size(200.dp)) {
                Text("SHOW", fontSize = 25.sp)
            }


        }
    }
}

@Composable
fun ResultsScreen(modifier: Modifier = Modifier) {


}


@Composable
fun PVASAT(modifier: Modifier = Modifier) {
    var kivalasztott by remember { mutableStateOf<Int?>(null) }
    var current by remember { mutableIntStateOf(Random.nextInt(1, 10)) }
    var prev by remember {mutableIntStateOf(0)}
    var potentialcur = 0
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000.milliseconds)
            prev = current
            potentialcur = Random.nextInt(1, 10)
            while (potentialcur == current) {
                potentialcur = Random.nextInt(1, 10)
            }
            current = potentialcur
            kivalasztott = null
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
    Text(current.toString(), fontSize = 50.sp)
    Text(prev.toString(), fontSize = 50.sp)
    Spacer(modifier = Modifier.height(30.dp))
    LazyVerticalGrid(
            columns = GridCells.Fixed(3),
    horizontalArrangement = Arrangement.spacedBy(5.dp),
    verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items((3..18).toList()) { szam ->
            val aktiv = kivalasztott == szam

            Button(

                onClick = { kivalasztott = szam },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.3f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (aktiv) {
                        if (kivalasztott == prev + current){
                            Color.Green
                        }
                        else {
                            Color.Red
                        }
                    }
                    else
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

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Text("Itt vannak a Beállítások", modifier = modifier)
}

/*@Preview(showBackground = true)
@Composable
*/