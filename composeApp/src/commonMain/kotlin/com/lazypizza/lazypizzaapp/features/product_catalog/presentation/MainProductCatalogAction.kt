package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.ProductCategory

sealed interface MainProductCatalogAction {
    data class OnProductClick(
        val product: Product,
    ) : MainProductCatalogAction

    data class OnCategorySelected(
        val productCategory: ProductCategory,
    ) : MainProductCatalogAction

    data class OnSearch(val query: String) : MainProductCatalogAction

    data object OnScrollCompleted : MainProductCatalogAction
}