package com.lazypizza.lazypizzaapp.navigation.locals

import androidx.compose.runtime.compositionLocalOf
import com.lazypizza.lazypizzaapp.navigation.model.NavItem

val LocalLazyPizzaNavItems = compositionLocalOf<List<NavItem>> {
    error("Items not provided")
}