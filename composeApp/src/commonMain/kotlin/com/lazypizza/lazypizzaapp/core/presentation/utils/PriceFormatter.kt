package com.lazypizza.lazypizzaapp.core.presentation.utils

fun Double.toPrice(): String {
    val rounded = kotlin.math.round(this * 100) / 100
    val intPart = rounded.toInt()
    val decimalPart = ((rounded - intPart) * 100).toInt()
    return "$intPart.${decimalPart.toString().padStart(2, '0')}"
}