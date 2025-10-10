package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.ProductCategory

sealed interface MainProductCatalogAction {
    data object OnProductClick : MainProductCatalogAction
    data class OnCategorySelected(
        val productCategory: ProductCategory,
    ) : MainProductCatalogAction
}