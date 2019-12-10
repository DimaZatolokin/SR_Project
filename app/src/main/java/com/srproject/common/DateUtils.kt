package com.srproject.common

import com.srproject.R
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT_PATTERN = "dd MMMM yyyy"

fun Long.toReadableDate(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale("ru", "UA"))
    return formatter.format(date)
}

fun String.toTimeStamp(): Long {
    val formatter = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale("ru", "UA"))
    val date = formatter.parse(this)
    return date?.time ?: 0
}

fun Int.getMonthNameRes() = when (this) {
    0 -> R.string.january
    1 -> R.string.february
    2 -> R.string.mart
    3 -> R.string.april
    4 -> R.string.may
    5 -> R.string.june
    6 -> R.string.july
    7 -> R.string.august
    8 -> R.string.september
    9 -> R.string.october
    10 -> R.string.november
    11 -> R.string.december
    else -> null
}

fun Date.nextMonth(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MONTH, 1)
    return calendar.time
}

fun Date.previousMonth(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MONTH, -1)
    return calendar.time
}

fun Date.getBeginMonthDate(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.apply {
        val month = get(Calendar.MONTH)
        val year = get(Calendar.YEAR)
        clear()
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        return timeInMillis
    }
}

fun Date.getEndMonthDate(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = this.nextMonth()
    calendar.apply {
        val month = get(Calendar.MONTH)
        val year = get(Calendar.YEAR)
        clear()
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        return timeInMillis - 1
    }
}