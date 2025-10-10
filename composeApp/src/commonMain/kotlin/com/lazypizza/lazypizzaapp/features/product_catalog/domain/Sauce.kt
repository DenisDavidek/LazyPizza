package com.lazypizza.lazypizzaapp.features.product_catalog.domain

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product.Sauce

fun getSauces(): List<Sauce> {
    return listOf(
        Sauce(
            id = 1,
            name = "Garlic Sauce",
            price = 0.59
        ),
        Sauce(
            id = 2,
            name = "BBQ Sauce",
            price = 0.59
        ),
        Sauce(
            id = 3,
            name = "Cheese Sauce",
            price = 0.89
        ),
        Sauce(
            id = 4,
            name = "Spicy Chili Sauce",
            price = 0.59
        )
    )
}
