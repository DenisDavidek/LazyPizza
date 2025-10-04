package com.lazypizza.lazypizzaapp.pizza_product.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ListScreen(
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = "ADD EXTRA TOPPINGS",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = onAddToCartClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Add to Cart for $12.99",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun ProductListPreview() {
    AppTheme {
        ListScreen(onAddToCartClick = {})
    }
}