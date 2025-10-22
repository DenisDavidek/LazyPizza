package com.lazypizza.lazypizzaapp

import androidx.compose.ui.window.ComposeUIViewController
import com.lazypizza.lazypizzaapp.app.di.initKoin

fun MainViewController() {
    ComposeUIViewController (
        configure = {
            initKoin()
        }
    ) {
        App()
    }
}