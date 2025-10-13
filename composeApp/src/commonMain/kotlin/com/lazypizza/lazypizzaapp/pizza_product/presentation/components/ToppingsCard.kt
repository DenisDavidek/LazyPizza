package com.lazypizza.lazypizzaapp.pizza_product.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.pizza_product.presentation.models.ToppingsUI
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToppingsCard(
    modifier: Modifier = Modifier,
    toppingsUI: ToppingsUI,
    increaseClick: () -> Unit,
    decreaseClick: () -> Unit,
) {
    var isAddedCart by rememberSaveable { mutableStateOf(false) }
    var count by rememberSaveable { mutableStateOf(1) }

    Card(
        modifier = modifier.size(height = 142.dp, width = 124.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(
            width = 1.dp, color = if (isAddedCart) {
                MaterialTheme.colorScheme.primary
            } else MaterialTheme.colorScheme.outline
        ),
        onClick = {
            isAddedCart = true
            increaseClick()
        },
        enabled = !isAddedCart
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        SubcomposeAsyncImage(
            model = toppingsUI.imageUrl,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            contentDescription = toppingsUI.name,
            contentScale = ContentScale.Crop,
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
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = toppingsUI.name,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (isAddedCart) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                OutlinedIconButton(
                    onClick = {
                        if (count <= 1) {
                            isAddedCart = false
                            decreaseClick()
                        } else {
                            count -= 1
                            decreaseClick()
                        }
                    },
                    border = BorderStroke(
                        width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
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
                    text = count.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                OutlinedIconButton(
                    onClick = {
                        count += 1
                        increaseClick()
                    },
                    border = BorderStroke(
                        width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    colors = IconButtonDefaults.iconButtonColors(
                        disabledContentColor = MaterialTheme.colorScheme.outline,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    enabled = count < 3,
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
        } else {
            Text(
                text = "$${toppingsUI.price}",
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview
@Composable
fun ToppingsCardPreview() {
    AppTheme {
        ToppingsCard(
            toppingsUI = ToppingsUI(
                imageUrl = "",
                name = "Hawaiian",
                price = 10.99
            ),
            increaseClick = {},
            decreaseClick = {}
        )
    }
}