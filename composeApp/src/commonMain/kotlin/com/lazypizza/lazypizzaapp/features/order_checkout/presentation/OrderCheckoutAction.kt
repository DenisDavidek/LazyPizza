package com.lazypizza.lazypizzaapp.features.order_checkout.presentation

import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.model.PickupTime

sealed interface OrderCheckoutAction {
    data object OnBackClick : OrderCheckoutAction
    data object OnProductDetailsToggle : OrderCheckoutAction
    data class OnPickupTimeSelected(val pickupTime: PickupTime) : OrderCheckoutAction
}