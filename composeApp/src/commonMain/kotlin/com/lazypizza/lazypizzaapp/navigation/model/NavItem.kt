package com.lazypizza.lazypizzaapp.navigation.model

import com.lazypizza.lazypizzaapp.navigation.LazyPizzaScreen
import org.jetbrains.compose.resources.DrawableResource

data class NavItem(
    val title: String,
    val icon: DrawableResource,
    val badge: String? = null,
    val screen: LazyPizzaScreen,
    val selected: Boolean,
)
