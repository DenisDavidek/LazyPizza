package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.ProductCategory

data class MainProductCatalogState(
    val selectedCategory: ProductCategory? = null,
    val products: List<Product> = emptyList(),
)