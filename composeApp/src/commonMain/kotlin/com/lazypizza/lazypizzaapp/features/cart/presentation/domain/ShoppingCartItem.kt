package com.lazypizza.lazypizzaapp.features.cart.presentation.domain

import com.benasher44.uuid.uuid4
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

data class ShoppingCartItem(
    val cartItemId: String = uuid4().toString(),
    val product: Product,
    var quantity: Int = 1
)
