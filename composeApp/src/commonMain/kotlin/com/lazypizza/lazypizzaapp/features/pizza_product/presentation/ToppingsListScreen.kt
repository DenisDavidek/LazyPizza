package com.lazypizza.lazypizzaapp.features.pizza_product.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.core.utils.toPrice
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.PrimaryGradientEnd
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.PrimaryGradientStart
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.components.GradientButton
import com.lazypizza.lazypizzaapp.features.pizza_product.ToppingsState
import com.lazypizza.lazypizzaapp.features.pizza_product.presentation.components.ToppingsCard
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.add_extra_toppings
import lazypizza.composeapp.generated.resources.add_to_cart
import org.jetbrains.compose.resources.stringResource

@Composable
fun ToppingsListScreen(
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: ToppingsState,
    pizza: Product,
    onIncreaseClick: (Product) -> Unit,
    onDecreaseClick: (Product) -> Unit
) {

    val totalPrice = pizza.price + state.selectedToppings.sumOf { it.price * it.quantity }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = stringResource(Res.string.add_extra_toppings),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.toppings) { topping ->
                    ToppingsCard(
                        topping = topping,
                        increaseClick = {
                            onIncreaseClick(topping)
                        },
                        decreaseClick = {
                            onDecreaseClick(topping)
                        }
                    )
                }
            }
        }

        GradientButton(
            onClick = onAddToCartClick,
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
            buttonText =
                if (totalPrice == 0.0) {
                    stringResource(Res.string.add_to_cart)
                } else {
                    "Add to Cart for $${totalPrice.toPrice()}"
                },
            colors = listOf(
                PrimaryGradientStart, PrimaryGradientEnd
            ),
            shadowColor = PrimaryGradientEnd
        )

    }
}
