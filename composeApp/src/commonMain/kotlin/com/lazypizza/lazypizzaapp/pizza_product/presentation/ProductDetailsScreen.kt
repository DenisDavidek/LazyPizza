@file:OptIn(ExperimentalMaterial3Api::class)

package com.lazypizza.lazypizzaapp.pizza_product.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductDetailScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val adaptiveWindow = currentWindowAdaptiveInfo()

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onClick
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.compose_multiplatform),
                            contentDescription = "Back",
                            tint = Color.Unspecified
                        )
                    }
                },
                title = {
                    /** no-op */
                }
            )
        },
        content = { innerPadding ->
            val isExpanded = adaptiveWindow.windowSizeClass
                .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)

            if (isExpanded) {
                println("MODE LANDSCAPE")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                ) {
                    Column(modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f)
                    ) {
                        PizzaDetailsScreen()
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
            }
            else {
                println("MODE PORTRAIT")

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    PizzaDetailsScreen()

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .padding(horizontal = 16.dp, vertical = 16.dp)
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
            onClick = {}
        )
    }
}