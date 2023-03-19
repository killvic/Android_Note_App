package com.example.note_app_hw.Help_Functions

import java.text.SimpleDateFormat
import java.util.*


fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("HH:mm")
    return format.format(date)
}

fun convertLongToDate(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd.MM")
    return format.format(date)
}

fun convertLongToYear(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy")
    return format.format(date)
}

fun truncateTimeFromMillis(currentTimeInMillis: Long): Long {
    // Get the number of milliseconds since midnight (i.e., the time portion)
    val timeInMillis = currentTimeInMillis % (24 * 60 * 60 * 1000)

    // Subtract the time portion from the current time to get the date
    val dateInMillis = currentTimeInMillis - timeInMillis

    // Return the date as a Long
    return dateInMillis
}