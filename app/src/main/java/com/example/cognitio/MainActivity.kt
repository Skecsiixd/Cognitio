package com.example.cognitio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults.colors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cognitio.ui.theme.CognitioTheme
import kotlinx.coroutines.delay

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
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScaffold(onBeginTest = { navController.navigate("night_test") })
        }
        composable("night_test") {
            NightTest { navController.popBackStack() }
        }
    }
}

@Composable
private fun MainScaffold(onBeginTest: () -> Unit) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val backgroundColor = Color(0xFFF7F1E5)

    NavigationSuiteScaffold(
        containerColor = backgroundColor,
        navigationSuiteColors = colors(backgroundColor),
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = { Icon(painterResource(it.icon), contentDescription = it.label) },
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
                    onBeginTest = onBeginTest,
                    modifier = Modifier.padding(innerPadding)
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
fun ScreenTimeElement(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var hasAccess by remember { mutableStateOf(hasUsageAccess(context)) }
    var minutes by remember { mutableLongStateOf(0L) }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        hasAccess = hasUsageAccess(context)
        if (hasAccess) minutes = todayScreenTimeMs(context) / 60_000
    }


    if (hasAccess) {
        Text("Mai képernyőidő: ${minutes / 60} óra ${minutes % 60} perc", modifier = modifier)
    } else {
        Text("HIBA: Nincs hozzáférés", modifier = modifier)

    }

}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onBeginTest: () -> Unit) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Jo napot, felhasznalo!", fontSize = 20.sp)
        ProvideTextStyle(TextStyle(fontSize = 24.sp)) {
            ScreenTimeElement()
        }
        Button(onClick = { onBeginTest() }, modifier = Modifier.size(200.dp)) {
            Text("BEGIN TEST", fontSize = 25.sp)
        }
    }


}


@Composable
fun ResultsScreen(modifier: Modifier = Modifier) {


}


@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var hasAccess by remember { mutableStateOf(hasUsageAccess(context)) }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { hasAccess = hasUsageAccess(context) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (hasAccess) {
            Text("Van engedely!")
        } else {
            Text("Nincs engedely")
            Button(onClick = { openUsageAccessSettings(context) }) {
                Text("Használati hozzáférés engedélyezése")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CognitioTheme {
        HomeScreen(onBeginTest = {})
    }
}