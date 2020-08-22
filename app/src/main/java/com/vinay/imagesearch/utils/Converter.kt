package com.vinay.imagesearch.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Converter Util.
 *
 * Using Kotlin's functional style advantage utils functions
 * written using extension functions
 *
 * @author vinay
 */

// Converts time millis to readable date format
fun Long.convertToDateString(format: String): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return SimpleDateFormat(format).format(calendar.time)
}

// Converts given string into title case. ( capitalises first letter)
fun String?.toTitleCase(): String {
    if (this.isNullOrBlank())
        return ""
    return this.substring(0, 1).toUpperCase() + this.substring(1)
}
