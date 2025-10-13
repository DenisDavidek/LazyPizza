package com.lazypizza.lazypizzaapp.features.product_catalog.domain

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product.IceCream

fun getSampleIceCreams(): List<IceCream> {
    return listOf(
        IceCream(
            id = 11, // Starting from 11 to avoid collision with pizza IDs
            name = "Vanilla Ice Cream",
            price = 2.49,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/iceCream%2Fvanilla.png?alt=media&token=f45da7d8-970c-41bb-be57-6b926fedf420"
        ),
        IceCream(
            id = 12,
            name = "Chocolate Ice Cream",
            price = 2.49,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/iceCream%2Fchocolate.png?alt=media&token=5898f30e-2aef-4a4b-8285-38ef89a439c6"
        ),
        IceCream(
            id = 13,
            name = "Strawberry Ice Cream",
            price = 2.49,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/iceCream%2Fstrawberry.png?alt=media&token=27bd0be8-d7e8-4c77-a13c-afefaaff948d"
        ),
        IceCream(
            id = 14,
            name = "Cookies Ice Cream",
            price = 2.79,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/iceCream%2Fcookies.png?alt=media&token=7dc4969a-993b-4d5e-8410-aee7b50b9679"
        ),
        IceCream(
            id = 15,
            name = "Pistachio Ice Cream",
            price = 2.99,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/iceCream%2Fpistachio.png?alt=media&token=bec5ac50-38ea-47cb-a435-61d6bb5e5a60"
        ),
        IceCream(
            id = 16,
            name = "Mango Sorbet",
            price = 2.69,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/iceCream%2Fmango%20sorbet.png?alt=media&token=a1933c92-0f72-4d63-9df8-94e0b3675e8a"
        )
    )
}