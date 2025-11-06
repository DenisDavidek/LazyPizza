package com.lazypizza.lazypizzaapp.core.utils

import com.lazypizza.lazypizzaapp.features.cart.presentation.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

fun List<Product>.countOverallPriceOld(): Double{

    return this.sumOf { product ->
        product.price * product.quantity

    }
}

fun List<ShoppingCartItem>.countOverallPrice(): Double{

    return this.sumOf { product ->
        product.product.price * product.quantity

    }
}