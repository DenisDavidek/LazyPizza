package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

data class MainProductCatalogState(
    val products: List<Product> = emptyList(),
    val scrollToIndex: Int? = null,
)