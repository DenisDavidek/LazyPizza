package com.lazypizza.lazypizzaapp.features.cart.presentation

import com.lazypizza.lazypizzaapp.features.cart.presentation.domain.ShoppingCartItem

/*data class CartState(val items: List<Product> = emptyList())*/
data class CartState(val items: List<ShoppingCartItem> = emptyList())

