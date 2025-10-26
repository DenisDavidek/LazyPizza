package com.lazypizza.lazypizzaapp.features.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lazypizza.lazypizzaapp.features.cart.presentation.components.CartItem
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.MainProductCatalogAction

@Composable
fun CartScreen(
    state: CartState,
    modifier: Modifier,
    onAction: (CartAction) -> Unit,
    onMainProductCatalogAction: (MainProductCatalogAction) -> Unit
) {

    LazyColumn(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        items(state.items, key = { product -> product.cartItemId }, itemContent = { product ->

            CartItem(
                modifier = Modifier.animateItem(),
                product = product,
                onCartItemDeleteClick = { product ->
                    onAction(CartAction.OnDeleteProductFromCart(product))
                    onMainProductCatalogAction(
                        MainProductCatalogAction.OnAddRemovedRecommendedAddon(product)
                    )
                },
                onIncrement = { product ->
                    onAction(CartAction.OnIncreaseQuantity(product))
                },
                onDecrement = { product ->
                    onAction(CartAction.OnDecreaseQuantity(product))
                })
        })

    }
}