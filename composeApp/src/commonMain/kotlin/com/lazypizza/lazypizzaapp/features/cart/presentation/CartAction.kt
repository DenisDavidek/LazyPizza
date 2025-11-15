package com.lazypizza.lazypizzaapp.features.cart.presentation

import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

sealed interface CartAction {

    data class OnAddToCart(val product: Product): CartAction
    data class OnIncreaseQuantity(val item: ShoppingCartItem) : CartAction
    data class OnDecreaseQuantity(val item: ShoppingCartItem) : CartAction
    data class OnDeleteProductFromCart(val item: ShoppingCartItem) : CartAction
    class OnClearCart(): CartAction
}
