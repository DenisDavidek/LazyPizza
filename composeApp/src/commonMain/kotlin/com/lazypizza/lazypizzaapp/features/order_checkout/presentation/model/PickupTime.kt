package com.lazypizza.lazypizzaapp.features.order_checkout.presentation.model

enum class PickupTime(
    val index: Int,
    val title: String,
) {
    EarlyAvailable(0, "Earliest available time"),
    Schedule(1, "Schedule time")
}