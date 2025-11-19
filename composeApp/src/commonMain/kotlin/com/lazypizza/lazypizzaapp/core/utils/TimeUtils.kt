package com.lazypizza.lazypizzaapp.core.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun getFormattedCurrentDateTime(): String {
    val now = Clock.System.now() // <-- THIS is correct in KMP
    val local = now.toLocalDateTime(TimeZone.currentSystemDefault())

    val monthName = local.month.name.lowercase().replaceFirstChar { it.titlecase() }
    val day = local.day
    val hour = local.hour.toString().padStart(2, '0')
    val minute = local.minute.toString().padStart(2, '0')

    return "$monthName $day, $hour:$minute"
}