package com.kozlovskiy.avitoweather.domain.util

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object ScaleUtils {
    fun hPaToMmHg(hPa: Int): Int {
        return (hPa / 1.33).roundToInt()
    }

    fun toDegreesString(temp: Double): String {
        return "${temp.roundToInt()}\u00B0"
    }

    fun timeFromSeconds(seconds: Long): String {
        val date = Date(seconds * 1000)
        return SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).format(date)
    }

    fun dateFromSeconds(seconds: Long): String {
        val date = Date(seconds * 1000)
        return SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(date)
    }

    fun dayOfWeekFromSeconds(seconds: Long): String {
        val date = Date(seconds * 1000)
        return SimpleDateFormat(WEEK_PATTERN, Locale.getDefault()).format(date)
    }

    private const val TIME_PATTERN = "HH:mm"
    private const val DATE_PATTERN = "dd MMMM"
    private const val WEEK_PATTERN = "EEEE"
}