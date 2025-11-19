package com.lazypizza.lazypizzaapp.features.order_checkout.presentation.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun getFormattedDateTime(
    dateTimeMillis: Long
): String {
    val date = Instant.fromEpochMilliseconds(dateTimeMillis)
    val local = date.toLocalDateTime(TimeZone.currentSystemDefault())

    val now = Clock.System.now()
    val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date

    val hour = local.hour.toString().padStart(2, '0')
    val minute = local.minute.toString().padStart(2, '0')

    return if (local.date == today) {
        "$hour:$minute"
    } else {
        val monthName = local.month.name.lowercase().replaceFirstChar { it.titlecase() }
        val day = local.day
        "$monthName $day, $hour:$minute"
    }
}