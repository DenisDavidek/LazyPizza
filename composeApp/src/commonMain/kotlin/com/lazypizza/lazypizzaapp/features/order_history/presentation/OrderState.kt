package com.lazypizza.lazypizzaapp.features.order_history.presentation

import com.lazypizza.lazypizzaapp.features.order_history.domain.Order

data class OrderState(val orders: List<Order> = emptyList())
