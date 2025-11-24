package com.lazypizza.lazypizzaapp.features.order_history.presentation

import com.lazypizza.lazypizzaapp.core.domain.model.User
import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.OrderCheckoutState

sealed interface OrderAction {
    data class OnCreateOrder(val orderCheckoutState: OrderCheckoutState, val currentUser: User?, val nextOrderId: Int): OrderAction
}