package com.lazypizza.lazypizzaapp.features.order_history.domain

import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem

class OrderItem(var id: Int = 1, val shoppingCartItems: List<ShoppingCartItem>, orderState: OrderState = OrderState.IN_PROGRESS) {
}