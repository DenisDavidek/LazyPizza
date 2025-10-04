package com.lazypizza.lazypizzaapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.lazypizza.lazypizzaapp.pizza_product.presentation.ProductDetailScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        ProductDetailScreen(
            onClick = {}
        )
    }
}