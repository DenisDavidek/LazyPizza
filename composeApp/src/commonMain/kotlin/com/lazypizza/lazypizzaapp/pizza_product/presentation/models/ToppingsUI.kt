package com.lazypizza.lazypizzaapp.pizza_product.presentation.models

import androidx.compose.ui.graphics.painter.Painter

data class ToppingsUI(
    val image: Painter,
    val name: String,
    val price: String
)
