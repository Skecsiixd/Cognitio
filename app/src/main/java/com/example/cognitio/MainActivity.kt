package com.example.cognitio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults.colors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun SettingsScreen(modifier: Modifier = Modifier) {
    Word_Play()
}

/*@Preview(showBackground = true)
@Composable
*/