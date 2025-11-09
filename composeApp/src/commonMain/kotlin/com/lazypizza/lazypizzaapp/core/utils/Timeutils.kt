package com.lazypizza.lazypizzaapp.core.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getFormattedCurrentDateTime(): String {
    val now = Clock.System.now()
    val local = now.toLocalDateTime(TimeZone.currentSystemDefault())

    // month enum name -> "September"
    val monthName = local.month.name.lowercase().replaceFirstChar { it.titlecase() }

    val day = local.dayOfMonth
    val hour = local.hour
    val minute = local.minute.toString().padStart(2, '0')

    val formattedTime = "$hour:$minute"

    return "$monthName $day, $formattedTime"
}