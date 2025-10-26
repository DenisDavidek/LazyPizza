package com.lazypizza.lazypizzaapp.core.utils

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

fun List<Product>.countOverallPrice(): Double{

    return this.sumOf { product ->
        product.price * product.quantity

    }
}