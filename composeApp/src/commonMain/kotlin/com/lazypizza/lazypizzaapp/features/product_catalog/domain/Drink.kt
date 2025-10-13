package com.lazypizza.lazypizzaapp.features.product_catalog.domain

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product.Drink

fun getSampleDrinks(): List<Drink> {
    return listOf(
        Drink(
            id = 21,
            name = "Mineral Water",
            price = 1.49,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/drink%2Fmineral%20water.png?alt=media&token=6556ead7-2009-414d-90cb-aa7cf1e19a36"
        ),
        Drink(
            id = 22,
            name = "7-Up",
            price = 1.89,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/drink%2F7-up.png?alt=media&token=f84505b0-22cf-4340-bd62-90e0b4e6cdd6"
        ),
        Drink(
            id = 23,
            name = "Pepsi",
            price = 1.99,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/drink%2Fpepsi.png?alt=media&token=0569a4b9-3853-40fa-9bb8-484f17a4d2df"
        ),
        Drink(
            id = 24,
            name = "Orange Juice ",
            price = 2.49,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/drink%2Forange%20juice.png?alt=media&token=9b8f7af1-f4b0-4644-9d16-58d828045a74"
        ),
        Drink(
            id = 25,
            name = "Apple Juice ",
            price = 2.29,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/drink%2Fapple%20juice.png?alt=media&token=f6d2488c-9f28-45d3-8227-16720a1d9a87"
        ),
        Drink(
            id = 26,
            name = "Iced Tea (Lemon) ",
            price = 2.19,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/drink%2Ficed%20tea.png?alt=media&token=8470cd75-b96a-4146-baa7-3dc178a53d28"
        )
    )
}