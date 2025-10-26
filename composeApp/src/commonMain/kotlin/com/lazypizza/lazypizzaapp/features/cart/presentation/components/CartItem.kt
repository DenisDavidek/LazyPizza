package com.lazypizza.lazypizzaapp.features.cart.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.lazypizza.lazypizzaapp.core.utils.toPrice
import com.lazypizza.lazypizzaapp.design_systems.components.IncrementDecrementCounter
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.cd_delete_from_cart
import lazypizza.composeapp.generated.resources.trash
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    product: Product,
    onCartItemDeleteClick: (product: Product) -> Unit,
    onIncrement: (product: Product) -> Unit,
    onDecrement: (product: Product) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .dropShadow(
                shape = RoundedCornerShape(size = 12.dp),
                shadow = Shadow(
                    radius = 4.dp,
                    spread = 4.dp,
                    color = Color.Black,
                )
            )
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(108.dp),
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
        }
        Column(
            modifier = Modifier
                .defaultMinSize(minHeight = 120.dp)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )


                OutlinedIconButton(
                    onClick = {
                        onCartItemDeleteClick(product)
                    },
                    border = BorderStroke(
                        width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.trash),
                        contentDescription = stringResource(Res.string.cd_delete_from_cart),
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }

            if (product is Product.Pizza) {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    product.toppings.forEach { topping ->
                        Text(
                            text = "${topping.quantity} x ${topping.name}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IncrementDecrementCounter(
                    product = product,
                    onIncrement = { product ->
                        onIncrement(product)
                    },
                    onDecrement = { product ->
                        onDecrement(product)
                    }
                )

                Column(
                    horizontalAlignment = Alignment.End,
                ) {

                    val totalPrice = if (product is Product.Pizza) {

                        "$${product.price.times(product.quantity).plus(product.toppings.sumOf { it.price.times(it.quantity) }).toPrice()}"

                    } else {
                        "$${product.price.times(product.quantity).toPrice()}"
                    }
                    Text(
                        text = totalPrice,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "${product.quantity} x $${product.price}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        }

    }
}