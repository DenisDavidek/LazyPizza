package com.lazypizza.lazypizzaapp.features.order_history.domain

import com.lazypizza.lazypizzaapp.core.domain.model.User
import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.OrderCheckoutState
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    val orders: Flow<List<Order>>
    suspend fun saveOrder(orderCheckoutState: OrderCheckoutState, currentUser: User?, nextOrderId: Int)
}