package com.lazypizza.lazypizzaapp.features.product_catalog.domain

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product.IceCream

fun getSampleIceCreams(): List<IceCream> {
    return listOf(
        IceCream(
            id = 11, // Starting from 11 to avoid collision with pizza IDs
            name = "Vanilla Ice Cream",
            price = 2.49
        ),
        IceCream(
            id = 12,
            name = "Chocolate Ice Cream",
            price = 2.49
        ),
        IceCream(
            id = 13,
            name = "Strawberry Ice Cream",
            price = 2.49
        ),
        IceCream(
            id = 14,
            name = "Cookies Ice Cream",
            price = 2.79
        ),
        IceCream(
            id = 15,
            name = "Pistachio Ice Cream",
            price = 2.99
        ),
        IceCream(
            id = 16,
            name = "Mango Sorbet",
            price = 2.69
        )
    )
}