package com.lazypizza.lazypizzaapp.features.order_checkout.presentation

import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.model.PickupTime
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

data class OrderCheckoutState(
    val selectedOption: PickupTime = PickupTime.EarlyAvailable,
    val earliestPickupTime: String = "",
    val orderDetailsExpanded: Boolean = false,
    val addOns: List<Product> = listOf(),
    val comment: String = "",
    val orderTotal: Double = 0.0,
)