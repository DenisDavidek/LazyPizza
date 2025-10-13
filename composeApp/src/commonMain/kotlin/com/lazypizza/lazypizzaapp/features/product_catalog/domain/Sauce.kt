package com.lazypizza.lazypizzaapp.features.product_catalog.domain

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product.Sauce

fun getSauces(): List<Sauce> {
    return listOf(
        Sauce(
            id = 17,
            name = "Garlic Sauce",
            price = 0.59,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/sauce%2FGarlic%20Sauce.png?alt=media&token=5ab3f798-0a03-4aa9-9313-fc500b92bd23"
        ),
        Sauce(
            id = 18,
            name = "BBQ Sauce",
            price = 0.59,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/sauce%2FBBQ%20Sauce.png?alt=media&token=00f20276-470e-48c1-9227-bf40a0f57486"
        ),
        Sauce(
            id = 19,
            name = "Cheese Sauce",
            price = 0.89,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/sauce%2FCheese%20Sauce.png?alt=media&token=d07e23f4-d9de-4429-97d9-3b461a3f1f8a"
        ),
        Sauce(
            id = 20,
            name = "Spicy Chili Sauce",
            price = 0.59,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/sauce%2FSpicy%20Chili%20Sauce.png?alt=media&token=9461226e-1965-43da-941d-ee275fd66f82"
        )
    )
}
