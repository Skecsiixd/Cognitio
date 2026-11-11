package com.example.cognitio

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar

fun todayScreenTimeMs(context: Context): Long {
    val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val home = context.packageManager.resolveActivity(
        Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0
    )?.activityInfo?.packageName

    val dayStart = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
    }.timeInMillis
    val now = System.currentTimeMillis()

    val resumed = HashMap<String, Long>() // activity -> mióta van előtérben
    var total = 0L

    // csak a mai napra eső részt számoljuk
    fun add(start: Long, end: Long) {
        if (end > dayStart) total += end - maxOf(start, dayStart)
    }

    fun closeAll(ts: Long) {
        resumed.values.forEach { add(it, ts) }
        resumed.clear()
    }

    // tegnaptól kérdezünk, hogy az éjfélen átnyúló használat is jól számolódjon
    val events = usm.queryEvents(dayStart - 24 * 60 * 60 * 1000L, now)
    val e = UsageEvents.Event()

    while (events.getNextEvent(e)) {
        if (e.packageName == home) continue // a launcher nem számít
        val key = "${e.packageName}/${e.className}"

        when (e.eventType) {
            UsageEvents.Event.ACTIVITY_RESUMED ->
                resumed.getOrPut(key) { e.timeStamp }

            UsageEvents.Event.ACTIVITY_PAUSED,
            UsageEvents.Event.ACTIVITY_STOPPED ->
                resumed.remove(key)?.let { add(it, e.timeStamp) }

            UsageEvents.Event.SCREEN_NON_INTERACTIVE,
            UsageEvents.Event.DEVICE_SHUTDOWN -> closeAll(e.timeStamp)
        }
    }
    closeAll(now) // ami most is előtérben van
    return total
}