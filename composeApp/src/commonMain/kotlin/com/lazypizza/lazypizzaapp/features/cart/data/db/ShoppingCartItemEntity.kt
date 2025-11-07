package com.lazypizza.lazypizzaapp.features.cart.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

@Entity(tableName = "shopping_cart_items")
data class ShoppingCartItemEntity(
    @PrimaryKey
    val cartItemId: String,
    val quantity: Int,
    val product: Product
) {
    fun toDomain(): ShoppingCartItem {
        return ShoppingCartItem(
            cartItemId = cartItemId,
            product = product,
            quantity = quantity
        )
    }
}

