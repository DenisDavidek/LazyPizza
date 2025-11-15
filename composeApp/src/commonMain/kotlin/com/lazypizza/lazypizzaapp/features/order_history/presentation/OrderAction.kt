package com.lazypizza.lazypizzaapp.features.order_history.presentation

import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem

sealed interface OrderAction {
    data class OnCreateOrder(val shoppingCartItems: List<ShoppingCartItem>): OrderAction
}