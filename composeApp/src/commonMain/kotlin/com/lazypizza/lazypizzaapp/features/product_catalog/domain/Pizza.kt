package com.lazypizza.lazypizzaapp.features.product_catalog.domain

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product.Pizza

fun getSamplePizzas(): List<Pizza> {
    return listOf(
        Pizza(
            id = 1,
            name = "Margherita",
            ingredients = listOf("Tomato sauce", "mozzarella", "fresh basil", "olive oil"),
            price = 8.99
        ),
        Pizza(
            id = 2,
            name = "Pepperoni",
            ingredients = listOf("Tomato sauce", "mozzarella", "pepperoni"),
            price = 9.99
        ),
        Pizza(
            id = 3,
            name = "Hawaiian",
            ingredients = listOf("Tomato sauce", "mozzarella", "ham", "pineapple"),
            price = 10.49
        ),
        Pizza(
            id = 4,
            name = "BBQ Chicken",
            ingredients = listOf("BBQ sauce", "mozzarella", "grilled chicken", "onion", "corn"),
            price = 11.49
        ),
        Pizza(
            id = 5,
            name = "Four Cheese",
            ingredients = listOf("Mozzarella", "gorgonzola", "parmesan", "ricotta"),
            price = 11.99
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
            price = 9.79
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
            price = 12.49
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
            price = 11.29
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
            price = 13.99
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
            price = 12.99
        )
    )
}