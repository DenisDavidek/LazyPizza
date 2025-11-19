package com.lazypizza.lazypizzaapp.features.order_checkout.presentation

import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.model.PickupTime
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

sealed interface OrderCheckoutAction {
    data object OnBackClick : OrderCheckoutAction
    data object OnProductDetailsToggle : OrderCheckoutAction
    data class OnAddOnPlusClick (
        val product: Product
    ) : OrderCheckoutAction
    data class OnPickupTimeSelected(val pickupTime: PickupTime) : OrderCheckoutAction
    data class OnCommentChange(val comment: String) : OrderCheckoutAction
    data class OnIncreaseQuantity(val item: ShoppingCartItem) : OrderCheckoutAction
    data class OnDecreaseQuantity(val item: ShoppingCartItem) : OrderCheckoutAction
    data class OnDeleteProductFromCart(val item: ShoppingCartItem) : OrderCheckoutAction
}