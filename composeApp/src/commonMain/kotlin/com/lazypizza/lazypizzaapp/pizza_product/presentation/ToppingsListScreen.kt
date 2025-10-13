package com.lazypizza.lazypizzaapp.pizza_product.presentation

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import com.lazypizza.lazypizzaapp.pizza_product.presentation.components.ToppingsCard
import com.lazypizza.lazypizzaapp.pizza_product.presentation.models.ToppingsUI
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToppingsListScreen(
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val toppings = addToppings
    var totalPrice by rememberSaveable { mutableStateOf(0.0) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = "ADD EXTRA TOPPINGS",
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
                items(toppings) { topping ->
                    ToppingsCard(
                        toppingsUI = topping,
                        increaseClick = {
                            totalPrice += topping.price
                        },
                        decreaseClick = {
                            totalPrice -= topping.price
                        }
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .dropShadow(CircleShape, Shadow(6.dp, Color(0xffF36B50).copy(alpha = .25f)))
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xffF9966F), Color(0xffF36B50)
                        )
                    ),
                    shape = CircleShape
                )
                .align(Alignment.BottomCenter),
            onClick = onAddToCartClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Text(
                text = if (totalPrice == 0.0) {
                    "Add to Cart"
                } else "Add to Cart for $$totalPrice",
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
    get() {
        return listOf(
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Fbacon.png?alt=media&token=b7ba25c6-d157-42fa-834c-7a5e4cc2dabd",
                name = "Bacon",
                price = 1.50
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Fcheese.png?alt=media&token=e5613461-b8af-457f-8be1-eafdeb8e6f77",
                name = "Extra Cheese",
                price = 1.00
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Fcorn.png?alt=media&token=8b1f9da8-d601-429e-9de7-c275f2223246",
                name = "Corn",
                price = 0.75
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Ftomato.png?alt=media&token=21b9d2e9-0bce-4043-9ba3-cbd20ce19c6b",
                name = "Tomato ",
                price = 2.00
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Folive.png?alt=media&token=726ed2fb-e71d-423a-b9a5-cfec7c685cba",
                name = "Olives ",
                price = 2.25
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Fpepperoni.png?alt=media&token=3b131b22-5f67-46fc-a390-3d7274eb3e60",
                name = "Pepperoni ",
                price = 1.25
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Fmashroom.png?alt=media&token=49a95ad7-a0ac-4a22-87d4-52c02fa68d09",
                name = "Mushrooms ",
                price = 1.50
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Fbasil.png?alt=media&token=113685df-07b1-4d3f-bbb9-acb5580f6e81",
                name = "Basil ",
                price = 1.00
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Fpineapple.png?alt=media&token=15d85115-143c-4448-ad82-651df3175267",
                name = "Pineapple ",
                price = 0.75
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Fonion.png?alt=media&token=a34b29ac-c316-4f3c-b1d3-612c99aa5803",
                name = "Onion ",
                price = 2.00
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Fchilli.png?alt=media&token=a8754fcd-5bf9-488b-af32-56d5019f3841",
                name = "Chili Peppers ",
                price = 2.25
            ),
            ToppingsUI(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazypizza-1999a.firebasestorage.app/o/toppings%2Fspinach.png?alt=media&token=2db5c8d1-7254-4714-be42-fe09ac3abb88",
                name = "Spinach",
                price = 1.25
            ),
        )
    }