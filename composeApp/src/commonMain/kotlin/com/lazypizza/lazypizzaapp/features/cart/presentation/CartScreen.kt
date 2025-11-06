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
        items(state.items, key = { product -> product.product.cartItemId }, itemContent = { shoppingCartItem ->

            CartItem(
                modifier = Modifier.animateItem(),
                shoppingCartItem = shoppingCartItem,
                onCartItemDeleteClick = { shoppingCartItem ->
                    onAction(CartAction.OnDeleteProductFromCart(shoppingCartItem))
                    onMainProductCatalogAction(
                        MainProductCatalogAction.OnAddRemovedRecommendedAddon(shoppingCartItem.product)
                    )
                },
                onIncrement = { shoppingCartItem ->
                    onAction(CartAction.OnIncreaseQuantity(shoppingCartItem))
                },
                onDecrement = { shoppingCartItem ->
                    onAction(CartAction.OnDecreaseQuantity(shoppingCartItem))
                })
        })

    }
}