package com.lazypizza.lazypizzaapp.core.utils

import kotlin.math.round

fun Double.toPrice(): String {
    val rounded = round(this * 100) / 100
    val intPart = rounded.toInt()
    val decimalPart = ((rounded - intPart) * 100).toInt()
    return "$intPart.${decimalPart.toString().padStart(2, '0')}"
}