@file:OptIn(ExperimentalMaterial3Api::class)

package com.lazypizza.lazypizzaapp.pizza_product.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.compose_multiplatform
import lazypizza.composeapp.generated.resources.hawaiian
import org.jetbrains.compose.resources.painterResource
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
            if (adaptiveWindow.windowSizeClass
                    .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) ) {

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
                        Image(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                                .size(240.dp),
                            painter = painterResource(Res.drawable.hawaiian),
                            contentDescription = "pizza",
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = "Margherita",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Tomato source, Mozzarella, Fresh basil, Olive oil",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 16.dp)) {

                        ListScreen(
                            modifier = Modifier.weight(1f),
                            onAddToCartClick = {}
                        )
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
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(240.dp),
                        painter = painterResource(Res.drawable.hawaiian),
                        contentDescription = "pizza",
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Margherita",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Tomato source, Mozzarella, Fresh basil, Olive oil",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        ListScreen(
                            onAddToCartClick = {}
                        )
                    }
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