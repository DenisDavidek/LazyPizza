package com.lazypizza.lazypizzaapp.features.cart.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.core.utils.toPrice
import com.lazypizza.lazypizzaapp.design_systems.PrimaryGradientEnd
import com.lazypizza.lazypizzaapp.design_systems.PrimaryGradientStart
import com.lazypizza.lazypizzaapp.design_systems.components.GradientButton
import com.lazypizza.lazypizzaapp.features.cart.presentation.components.AddonItem
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.recommended_to_add_to_your_order
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecommendedAddonsScreen(
    modifier: Modifier,
    products: List<Product>,
    totalPrice: Double,
    onProductAddClick: (Product) -> Unit,
    onProceedToCheckoutClick: () -> Unit
) {

    Column(
        modifier = modifier.wrapContentHeight(), // This is key
        verticalArrangement = Arrangement.Top
    ) {
        Column(modifier = Modifier.weight(1f, fill = false)) {
            Text(
                text = stringResource(Res.string.recommended_to_add_to_your_order).uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 12.dp)
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp, top = 0.dp, bottom = 20.dp)
            ) {
                items(items = products, key = { product -> product.cartItemId }, itemContent = { product ->
                    AddonItem(
                        modifier = Modifier.width(180.dp).wrapContentHeight(),
                        product = product,
                        onProductAddClick = { product ->
                            onProductAddClick(product)
                        }
                    )
                })
            }
        }
        GradientButton(
            onClick = onProceedToCheckoutClick,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 12.dp),
            buttonText = "Proceed to Checkout ($${totalPrice.toPrice()})",
            colors = listOf(
                PrimaryGradientStart, PrimaryGradientEnd
            ),
            shadowColor = PrimaryGradientEnd
        )
    }
}