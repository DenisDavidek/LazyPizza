package com.lazypizza.lazypizzaapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface LazyPizzaScreen {

    @Serializable
    object MainProductCatalog : LazyPizzaScreen

    @Serializable
    object ProductDetail : LazyPizzaScreen


}