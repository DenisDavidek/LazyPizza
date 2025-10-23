package com.lazypizza.lazypizzaapp.features.cart.presentation

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

sealed interface CartAction {

    data class OnAddToCart(val product: Product): CartAction
}