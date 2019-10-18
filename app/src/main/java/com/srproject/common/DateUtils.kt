package com.srproject.common

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