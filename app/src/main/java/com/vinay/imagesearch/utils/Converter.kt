package com.mrittica.utils

import java.text.SimpleDateFormat
import java.util.*


fun Long.convertToDateString(format: String): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return SimpleDateFormat(format).format(calendar.time)
}

fun String?.toTitleCase(): String {
    if (this.isNullOrBlank())
        return ""
    return this.substring(0, 1).toUpperCase() + this.substring(1)
}
