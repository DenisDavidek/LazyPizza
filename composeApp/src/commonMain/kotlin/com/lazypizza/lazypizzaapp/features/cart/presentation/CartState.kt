package com.lazypizza.lazypizzaapp.features.cart.presentation

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

data class CartState(val items: List<Product> = emptyList())


