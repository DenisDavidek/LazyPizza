package com.lazypizza.lazypizzaapp.features.cart.presentation

import androidx.lifecycle.ViewModel
import com.lazypizza.lazypizzaapp.features.cart.presentation.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()

    fun onAction(action: CartAction) {
        when (action) {
            is CartAction.OnAddToCart -> {

                val productToAdd = action.product

                _cartState.update { currentState ->
                    // Find if an identical product configuration already exists.
                    val existingIdenticalItem = currentState.items.find {
                        // It must be the same base product
                        it.product.id == productToAdd.id &&
                                // And if it's a pizza, the toppings must also match
                                (it.product as? Product.Pizza)?.toppings == (productToAdd as? Product.Pizza)?.toppings
                    }

                    val updatedCartItems = if (existingIdenticalItem == null) {
                        // If the item is new, add it to the list.
                        // The unique cartItemId is now generated automatically by the ShoppingCartItem constructor.
                        val newCartItem = ShoppingCartItem(
                            product = productToAdd,
                            quantity = 1
                        )
                        currentState.items + newCartItem
                    } else {
                        // If an identical item exists, we update its quantity.
                        currentState.items.map { item ->
                            if (item.cartItemId == existingIdenticalItem.cartItemId) {
                                item.copy(quantity = item.quantity + 1)
                            } else {
                                item
                            }
                        }
                    }
                    currentState.copy(items = updatedCartItems)
                }
            }

            is CartAction.OnDeleteProductFromCart -> {

                val itemToDelete = action.item
                _cartState.update { currentState ->
                    val updatedCartItems =
                        currentState.items.filterNot { it.cartItemId == itemToDelete.cartItemId }
                    currentState.copy(items = updatedCartItems)
                }
            }

            is CartAction.OnIncreaseQuantity -> {
                val itemToIncrease = action.item
                _cartState.update { currentState ->
                    val updatedCartItems = currentState.items.map { item ->
                        if (item.cartItemId == itemToIncrease.cartItemId) {
                            item.copy(quantity = item.quantity + 1)
                        } else {
                            item
                        }
                    }
                    currentState.copy(items = updatedCartItems)
                }
            }

            is CartAction.OnDecreaseQuantity -> {
                val itemToDecrease = action.item
                _cartState.update { currentState ->
                    val updatedCartItems = currentState.items.mapNotNull { item ->
                        if (item.cartItemId == itemToDecrease.cartItemId) {
                            if (item.quantity > 1) {
                                item.copy(quantity = item.quantity - 1)
                            } else {
                                null
                            }
                        } else {
                            item
                        }
                    }
                    currentState.copy(items = updatedCartItems)
                }
            }
        }
    }
}