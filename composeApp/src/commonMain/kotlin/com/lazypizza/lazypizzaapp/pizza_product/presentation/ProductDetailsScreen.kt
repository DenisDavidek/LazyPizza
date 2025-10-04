@file:OptIn(ExperimentalMaterial3Api::class)

package com.lazypizza.lazypizzaapp.pizza_product.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
            if(adaptiveWindow.windowSizeClass
                .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) ) {

                Row(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    println("MODE LANDSCAPE")
                    DetailScreen(
                        modifier = Modifier.weight(1f)
                    )

                    ListScreen(
                        modifier = Modifier.weight(1f),
                        onAddToCartClick = {}
                    )
                }
            }
            else {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    println("MODE PORTRAIT")
                    DetailScreen()

                    ListScreen(
                        onAddToCartClick = {}
                    )
                }
            }

        }
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