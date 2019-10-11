package com.srproject.common

import java.text.SimpleDateFormat
import java.util.*

fun Long.toReadableDate(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("uk", "UA"))
    return formatter.format(date)
}