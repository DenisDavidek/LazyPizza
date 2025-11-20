package com.lazypizza.lazypizzaapp.features.order_checkout.presentation

import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.model.PickupTime
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

data class OrderCheckoutState(
    val selectedOption: PickupTime = PickupTime.EarlyAvailable,
    val earliestPickupTime: String = "",
    val isOrderDetailsExpanded: Boolean = false,
    val addOns: List<Product> = listOf(),
    val products: List<ShoppingCartItem> = emptyList(),
    val comment: String = "",
    val orderTotal: Double = 0.0,
    val isPickDateDialogVisible: Boolean = false,
    val isPickTimeDialogVisible: Boolean = false,
    val datePickerSelectedDateMillis: Long? = null,
    val pickTimeError: String? = null,
    val isPickupTimeValid: Boolean = true,
)