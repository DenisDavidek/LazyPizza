package com.lazypizza.lazypizzaapp.features.product_catalog.domain

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product.Pizza

fun getSamplePizzas(): List<Pizza> {
    return listOf(
        Pizza(
            id = 1,
            name = "Margherita",
            ingredients = listOf("Tomato sauce", "mozzarella", "fresh basil", "olive oil"),
            price = 8.99,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/pizza%2FMargherita.png?alt=media&token=9dca7e9a-e947-4b1d-a583-7c548bd432d2"
        ),
        Pizza(
            id = 2,
            name = "Pepperoni",
            ingredients = listOf("Tomato sauce", "mozzarella", "pepperoni"),
            price = 9.99,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/pizza%2FPepperoni.png?alt=media&token=ae58369d-12d7-49c6-8c22-f86841df7926"
        ),
        Pizza(
            id = 3,
            name = "Hawaiian",
            ingredients = listOf("Tomato sauce", "mozzarella", "ham", "pineapple"),
            price = 10.49,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/pizza%2FHawaiian.png?alt=media&token=0e9e4dcc-6467-4ed4-b0de-4d629f1f4d85"
        ),
        Pizza(
            id = 4,
            name = "BBQ Chicken",
            ingredients = listOf("BBQ sauce", "mozzarella", "grilled chicken", "onion", "corn"),
            price = 11.49,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/pizza%2FBBQ%20Chicken.png?alt=media&token=0d4b3df1-5062-45f2-8c08-ad9b1b3aea5d"
        ),
        Pizza(
            id = 5,
            name = "Four Cheese",
            ingredients = listOf("Mozzarella", "gorgonzola", "parmesan", "ricotta"),
            price = 11.99,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/pizza%2FFour%20Cheese.png?alt=media&token=fb4b7fc6-527e-407f-be06-079f4d852927"
        ),
        Pizza(
            id = 6,
            name = "Veggie Delight",
            ingredients = listOf(
                "Tomato sauce",
                "mozzarella",
                "mushrooms",
                "olives",
                "bell pepper",
                "onion",
                "corn"
            ),
            price = 9.79,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/pizza%2FVeggie%20Delight.png?alt=media&token=952d2050-1e01-4c2f-a108-86b48ca86e4a"
        ),
        Pizza(
            id = 7,
            name = "Meat Lovers",
            ingredients = listOf(
                "Tomato sauce",
                "mozzarella",
                "pepperoni",
                "ham",
                "bacon",
                "sausage"
            ),
            price = 12.49,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/pizza%2FMeat%20Lovers.png?alt=media&token=8b009a68-7240-4141-a08a-c43cb3f11512"
        ),
        Pizza(
            id = 8,
            name = "Spicy Inferno",
            ingredients = listOf(
                "Tomato sauce",
                "mozzarella",
                "spicy salami",
                "jalapeños",
                "red chili pepper",
                "garlic"
            ),
            price = 11.29,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/pizza%2FSpicy%20Inferno.png?alt=media&token=1fc2731d-0238-4f91-9dfa-bf87a1053ca7"
        ),
        Pizza(
            id = 9,
            name = "Seafood Special",
            ingredients = listOf(
                "Tomato sauce",
                "mozzarella",
                "shrimp",
                "mussels",
                "squid",
                "parsley"
            ),
            price = 13.99,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/pizza%2FSeafood%20Special.png?alt=media&token=19160933-ad92-477f-9fb3-be8701954dd3"
        ),
        Pizza(
            id = 10,
            name = "Truffle Mushroom",
            ingredients = listOf(
                "Cream sauce",
                "mozzarella",
                "mushrooms",
                "truffle oil",
                "parmesan"
            ),
            price = 12.99,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/pizza%2FTruffle%20Mushroom.png?alt=media&token=118adb25-9045-4cf4-8010-145e0967df50"
        )
    )
}