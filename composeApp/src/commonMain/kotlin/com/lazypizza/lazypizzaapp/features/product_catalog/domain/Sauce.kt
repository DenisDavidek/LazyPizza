package com.lazypizza.lazypizzaapp.features.product_catalog.domain

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product.Sauce

fun getSauces(): List<Sauce> {
    return listOf(
        Sauce(
            id = 17,
            name = "Garlic Sauce",
            price = 0.59
        ),
        Sauce(
            id = 18,
            name = "BBQ Sauce",
            price = 0.59
        ),
        Sauce(
            id = 19,
            name = "Cheese Sauce",
            price = 0.89
        ),
        Sauce(
            id = 20,
            name = "Spicy Chili Sauce",
            price = 0.59
        )
    )
}
