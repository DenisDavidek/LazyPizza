package com.lazypizza.lazypizzaapp.features.product_catalog.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.lazypizza.lazypizzaapp.core.utils.toPrice
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.trash
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isAddedToCart by rememberSaveable {
        mutableStateOf(false)
    }
    var cartCount by rememberSaveable {
        mutableStateOf(1)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick, enabled = product is Product.Pizza)
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

                if (isAddedToCart) {
                    OutlinedIconButton(
                        onClick = {
                            isAddedToCart = false
                            cartCount = 1
                        },
                        border = BorderStroke(
                            width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.trash),
                            contentDescription = "Delete from cart",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            if (product is Product.Pizza) {
                Text(
                    text = product.ingredients.joinToString(separator = ", "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.weight(1f))
            } else {
                Spacer(Modifier.weight(1f))
            }

            when (product) {
                is Product.Pizza -> {
                    Text(
                        text = "$${product.price}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                is Product.IceCream -> {
                    ProductCart(
                        onIncrement = {
                            cartCount++
                        },
                        onDecrement = {
                            if (cartCount <= 1) {
                                isAddedToCart = false
                            } else {
                                cartCount--
                            }
                        },

                        price = product.price * cartCount,
                        originalPrice = product.price,
                        count = cartCount,
                        isAddedToCart = isAddedToCart,
                        onAddToCart = {
                            isAddedToCart = true
                        }
                    )
                }

                is Product.Sauce -> {
                    ProductCart(
                        onIncrement = {
                            cartCount++
                        },
                        onDecrement = {
                            if (cartCount <= 1) {
                                isAddedToCart = false
                            } else {
                                cartCount--
                            }
                        },
                        price = product.price * cartCount,
                        originalPrice = product.price,
                        count = cartCount,
                        isAddedToCart = isAddedToCart,
                        onAddToCart = {
                            isAddedToCart = true
                        }
                    )
                }

                is Product.Drink -> {
                    ProductCart(
                        onIncrement = {
                            cartCount++
                        },
                        onDecrement = {
                            if (cartCount <= 1) {
                                isAddedToCart = false
                            } else {
                                cartCount--
                            }
                        },
                        price = product.price * cartCount,
                        originalPrice = product.price,
                        count = cartCount,
                        isAddedToCart = isAddedToCart,
                        onAddToCart = {
                            isAddedToCart = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductCart(
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onAddToCart: () -> Unit,
    price: Double,
    isAddedToCart: Boolean,
    originalPrice: Double,
    count: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isAddedToCart) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                OutlinedIconButton(
                    onClick = onDecrement,
                    border = BorderStroke(
                        width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant
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
                    onClick = onIncrement,
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
        } else {
            Text(
                text = "$${price}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (isAddedToCart) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$${price.toPrice()}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "$count x $${originalPrice}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            OutlinedButton(
                onClick = {
                    onAddToCart()
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = .8f)
                )
            ) {
                Text(
                    text = "Add to Cart",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}