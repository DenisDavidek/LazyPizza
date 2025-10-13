package com.lazypizza.lazypizzaapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface LazyPizzaScreen {

    @Serializable
    object MainProductCatalog : LazyPizzaScreen

    @Serializable
    data class ProductDetail(
        val productJson: String,
    ) : LazyPizzaScreen


}