package com.lazypizza.lazypizzaapp

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun onBackClick(action: () -> Unit) {
    BackHandler {
        action()
    }
}