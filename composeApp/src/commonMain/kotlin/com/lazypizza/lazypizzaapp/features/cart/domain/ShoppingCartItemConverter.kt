package com.lazypizza.lazypizzaapp.features.cart.domain

import com.lazypizza.lazypizzaapp.features.cart.data.db.ShoppingCartItemEntity

fun List<ShoppingCartItemEntity>.toDomain(): List<ShoppingCartItem> {
    return this.map { it.toDomain() }
}

fun ShoppingCartItem.toEntity(): ShoppingCartItemEntity {
    return ShoppingCartItemEntity(
        cartItemId = this.cartItemId,
        product = this.product,
        quantity = this.quantity
    )
}
