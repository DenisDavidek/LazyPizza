package com.lazypizza.lazypizzaapp

import androidx.compose.ui.window.ComposeUIViewController
import com.lazypizza.lazypizzaapp.app.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController() : UIViewController {
   return ComposeUIViewController (
        configure = {
            initKoin()
        }
    ) {
        App()
    }
}