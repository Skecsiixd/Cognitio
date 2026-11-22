package com.example.cognitio

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cognitio.data.SupabaseProvider
import com.example.cognitio.data.TestResult

import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun Calendar(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var passDate: LocalDate?
    passDate = null
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            CalendarElement(
                onFinished = { passDate = it },
                onBegin = { navController.navigate("night_test") })
        }
        composable("night_test") {
            DatePage(modifier, passDate)
        }
    }
}

@Composable
fun CalendarElement(
    modifier: Modifier = Modifier,
    onFinished: (Date: LocalDate?) -> Unit,
    onBegin: () -> Unit
) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(LocalDate.now().month.getDisplayName(TextStyle.FULL, Locale.getDefault()))
        HorizontalCalendar(
            state = rememberCalendarState(),
            dayContent = { day ->
                val isToday =
                    day.position == DayPosition.MonthDate && day.date == LocalDate.now()

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .clickable(enabled = day.position == DayPosition.MonthDate) {
                            if (LocalDate.now() >= day.date) {
                                onBegin()
                                selectedDate = day.date
                                onFinished(selectedDate)
                            }
                        }
                        .background(
                            color = if (isToday) Color.Red else Color.Transparent,
                            shape = CircleShape
                        ),

                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.date.dayOfMonth.toString(),
                        color = if (isToday) Color.Green else if (day.date.month != LocalDate.now().month) Color.LightGray else Color.Unspecified
                    )
                }
            }
        )

    }
}

@Composable
fun DatePage(modifier: Modifier = Modifier, SelectedDate: LocalDate?) {
    var results by remember { mutableStateOf<List<TestResult>>(emptyList()) }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(SelectedDate.toString(), fontSize = 50.sp)
        Text("Eredmenyek: ", fontSize = 30.sp)
        LaunchedEffect(Unit) {
            try {
                results = SupabaseProvider.getTestResults()
            } catch (e: Exception) {
                Log.e("Supa", e.message.toString())
            }
        }

        results.forEach {
            if (it.testDate == SelectedDate.toString()) {
                when (it.testType) {
                    "PVT" -> Text(
                        "Reakcioido atlaga: ${it.score.toInt()}ms",
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Italic
                    )

                    "PVASAT" -> Text(
                        "PVASAT pontszam: ${it.score.toInt()}", fontSize = 30.sp,
                        fontStyle = FontStyle.Italic
                    )

                    "WORDPLAY" -> Text(
                        "Megjegyzett szavak: ${it.score.toInt()}", fontSize = 30.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}