package com.lazypizza.lazypizzaapp.features.order_history.presentation

import com.lazypizza.lazypizzaapp.features.order_history.domain.OrderItem

data class OrderState(val orders: List<OrderItem> = emptyList())
