package com.lazypizza.lazypizzaapp.pizza_product.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.hawaiian
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PizzaDetailsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxWidth()) {
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
}

@Preview
@Composable
fun ProductDetailsPreview() {
    AppTheme {
        PizzaDetailsScreen()
    }
}