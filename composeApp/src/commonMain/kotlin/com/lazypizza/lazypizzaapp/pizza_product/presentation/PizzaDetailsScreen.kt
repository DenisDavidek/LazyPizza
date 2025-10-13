package com.lazypizza.lazypizzaapp.pizza_product.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.hawaiian
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PizzaDetailsScreen(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(240.dp),
            loading = {
                Box(
                    modifier = Modifier.size(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            },
            model = product.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Text(
            text = product.name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(4.dp))

        if (product is Product.Pizza) {
            Text(
                text = product.ingredients.joinToString(separator = ", "),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
fun ProductDetailsPreview() {
    AppTheme {
        PizzaDetailsScreen(
            product = Product.Pizza(
                id = 0,
                name = "Pizza",
                ingredients = listOf(),
                price = 0.0,
                imageUrl = ""
            )
        )
    }
}