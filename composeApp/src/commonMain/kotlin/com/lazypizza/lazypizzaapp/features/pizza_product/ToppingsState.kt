package com.lazypizza.lazypizzaapp.features.pizza_product

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

data class ToppingsState(
    val toppings: List<Product> = emptyList(),
    val selectedToppings: List<Product> = emptyList()
)
