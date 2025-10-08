package com.lazypizza.lazypizzaapp.pizza_product.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.pizza_product.presentation.components.ToppingsCard
import com.lazypizza.lazypizzaapp.pizza_product.presentation.models.ToppingsUI
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.bbq_chicken
import lazypizza.composeapp.generated.resources.four_cheese
import lazypizza.composeapp.generated.resources.hawaiian
import lazypizza.composeapp.generated.resources.margherita
import lazypizza.composeapp.generated.resources.pepperoni
import lazypizza.composeapp.generated.resources.seafood_special
import lazypizza.composeapp.generated.resources.spicy_inferno
import lazypizza.composeapp.generated.resources.truffle_mushroom
import lazypizza.composeapp.generated.resources.veggie_delight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToppingsListScreen(
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
   val toppings = addToppings

    Box(
        modifier = modifier
            .fillMaxSize()
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

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(bottom = 80.dp), // To avoid overlapping with the button
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(toppings) { topping ->
                    ToppingsCard(
                        toppingsUI = topping,
                        increaseClick = { },
                        decreaseClick = { }
                    )
                }
            }
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
        ToppingsListScreen(onAddToCartClick = {})
    }
}

val addToppings: List<ToppingsUI>
    @Composable
    get()  {
        return listOf(
            ToppingsUI(
                image = painterResource(Res.drawable.hawaiian),
                name = "Pepperoni",
                price = "$1.50"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.bbq_chicken),
                name = "Mushroom",
                price = "$1.00"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.four_cheese),
                name = "Onion",
                price = "$0.75"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.seafood_special),
                name = "Sausage",
                price = "$2.00"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.truffle_mushroom),
                name = "Bacon",
                price = "$2.25"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.veggie_delight),
                name = "Olives",
                price = "$1.25"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.hawaiian),
                name = "Pepperoni",
                price = "$1.50"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.truffle_mushroom),
                name = "Mushroom",
                price = "$1.00"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.bbq_chicken),
                name = "Onion",
                price = "$0.75"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.spicy_inferno),
                name = "Sausage",
                price = "$2.00"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.pepperoni),
                name = "Bacon",
                price = "$2.25"
            ),
            ToppingsUI(
                image = painterResource(Res.drawable.margherita),
                name = "Olives",
                price = "$1.25"
            ),
        )
    }