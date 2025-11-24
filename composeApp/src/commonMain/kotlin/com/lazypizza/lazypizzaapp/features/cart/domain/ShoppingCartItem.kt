package com.lazypizza.lazypizzaapp.features.cart.domain

import com.benasher44.uuid.uuid4
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import kotlinx.serialization.Serializable

@Serializable
data class ShoppingCartItem(
    val cartItemId: String = uuid4().toString(),
    val product: Product,
    var quantity: Int = 1
)
