package com.lazypizza.lazypizzaapp.features.cart.presentation

import androidx.lifecycle.ViewModel
import com.benasher44.uuid.uuid4
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

                    val existingIdenticalItem = currentState.items.find {
                        it.id == productToAdd.id && (it as? Product.Pizza)?.toppings == (productToAdd as? Product.Pizza)?.toppings
                    }

                    val updatedCartItems = if (existingIdenticalItem == null) {

                        val newCartItem = productToAdd.copyNewQuantity(1)
                            .copyCartItemId(newCartItemId = uuid4().toString())
                        currentState.items + newCartItem
                    } else {
                        currentState.items.map { item ->
                            if (item.cartItemId == existingIdenticalItem.cartItemId) {
                                item.copyNewQuantity(item.quantity + 1)
                            } else {
                                item
                            }
                        }
                    }
                    currentState.copy(items = updatedCartItems)
                }
            }

            is CartAction.OnDeleteProductFromCart -> {

                val productToDelete = action.product
                _cartState.update { currentState ->
                    // Use the unique cartItemId to delete
                    val updatedCartItems =
                        currentState.items.filterNot { it.cartItemId == productToDelete.cartItemId }
                    currentState.copy(items = updatedCartItems)
                }
            }

            is CartAction.OnIncreaseQuantity -> {

                val productToIncrease = action.product
                _cartState.update { currentState ->
                    val updatedCartItems = currentState.items.map { item ->
                        // Use the unique cartItemId to find the item
                        if (item.cartItemId == productToIncrease.cartItemId) {
                            item.copyNewQuantity(item.quantity + 1)
                        } else {
                            item
                        }
                    }
                    currentState.copy(items = updatedCartItems)
                }

            }

            is CartAction.OnDecreaseQuantity -> {

                val productToDecrease = action.product
                _cartState.update { currentState ->
                    val updatedCartItems = currentState.items.map { item ->
                        if (item.cartItemId == productToDecrease.cartItemId) {
                            if (item.quantity > 1) {
                                item.copyNewQuantity(item.quantity - 1)
                            } else {
                                // If quantity is 1, it remains 1, do nothing.
                                // Deletion is handled by OnDeleteProductFromCart.
                                item
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