package com.lazypizza.lazypizzaapp.features.order_history.presentation.utils

import com.lazypizza.lazypizzaapp.core.utils.getCurrentTimeStamp

fun generateOrderNumber(): Int {
    val timestamp = getCurrentTimeStamp()
    return (timestamp % 100000).toInt()
}
