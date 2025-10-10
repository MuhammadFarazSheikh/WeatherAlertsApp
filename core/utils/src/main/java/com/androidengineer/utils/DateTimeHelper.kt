package com.androidengineer.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

const val DATE_TIME_FORMAT_OLD = "yyyy-MM-dd HH:mm"
const val DATE_TIME_FORMAT_NEW = "EEEE, MMMM dd, yyyy"
const val DATE_FORMAT_OLD = "yyyy-MM-dd"
const val DATE_FORMAT_NEW = "MMMM d, yyyy"

private val formatterCache = java.util.concurrent.ConcurrentHashMap<String, DateTimeFormatter>()

private fun getFormatter(pattern: String): DateTimeFormatter {
    return formatterCache.getOrPut(pattern) {
        DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)
    }
}

fun formatDateTime(
    input: String,
    oldPattern: String,
    newPattern: String
): String {
    return try {
        val inputFormatter = getFormatter(oldPattern)
        val outputFormatter = getFormatter(newPattern)
        val date = LocalDate.parse(input.trim(), inputFormatter)
        date.format(outputFormatter)
    } catch (e: Exception) {
        ""
    }
}

