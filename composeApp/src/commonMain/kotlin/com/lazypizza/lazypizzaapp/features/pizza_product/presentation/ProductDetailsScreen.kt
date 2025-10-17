@file:OptIn(ExperimentalMaterial3Api::class)

package com.lazypizza.lazypizzaapp.features.pizza_product.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductDetailScreen(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val adaptiveWindow = currentWindowAdaptiveInfo()

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .08f)
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        content = { innerPadding ->
            val isExpanded = adaptiveWindow.windowSizeClass
                .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)

            if (isExpanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f)
                    ) {
                        PizzaDetailsScreen(product = product)
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        ToppingsList(modifier = Modifier.weight(1f))
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxSize()
                ) {
                    PizzaDetailsScreen(
                        product = product
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                            )
                    ) {
                        ToppingsList(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    )
}

@Composable
private fun ToppingsList(modifier: Modifier = Modifier) {
    ToppingsListScreen(
        modifier = modifier,
        onAddToCartClick = {}
    )
}

@Preview
@Composable
private fun ProductCatalogPreview() {
    AppTheme {
        ProductDetailScreen(
            product = Product.Pizza(
                id = 0,
                name = "Pizza",
                ingredients = listOf(),
                price = 0.0,
                imageUrl = ""
            ),
            onClick = {},
        )
    }
}