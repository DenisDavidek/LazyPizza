package com.lazypizza.lazypizzaapp.design_systems.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product

@Composable
fun IncrementDecrementCounter(
    product: Product,
    onIncrement: (product: Product) -> Unit,
    onDecrement: (product: Product) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        OutlinedIconButton(
            onClick = { onDecrement(product) },
            border = BorderStroke(
                width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant
            ),
            enabled = product.quantity > 1,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrement",
                modifier = Modifier.size(18.dp)
            )
        }

        Text(
            text = product.quantity.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        OutlinedIconButton(
            onClick = { onIncrement(product) },
            border = BorderStroke(
                width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increment",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}