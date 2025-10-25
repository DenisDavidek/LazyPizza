package com.lazypizza.lazypizzaapp.features.cart.presentation

import androidx.lifecycle.ViewModel
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

                    val existingItem = currentState.items.find { it.id == productToAdd.id }

                    val updatedCartItems = if (existingItem == null) {
                        currentState.items + productToAdd
                    } else {
                        currentState.items.map { item ->
                            if (item.id == existingItem.id) {

                                item.copyNewQuantity(newQuantity = item.quantity + 1)
                            } else {
                                // This is a different item, leave it as is.
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

                    val updatedCartItems =
                        currentState.items.filterNot { it.id == productToDelete.id }

                    currentState.copy(items = updatedCartItems)
                }
            }

            is CartAction.OnIncreaseQuantity -> {

                val productToIncrease = action.product

                _cartState.update { currentState ->
                    val updatedCartItems = currentState.items.map { item ->

                        if (item.id == productToIncrease.id) {
                            item.copyNewQuantity(newQuantity = item.quantity + 1)
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

                        if (item.id == productToDecrease.id) {

                            if (item.quantity > 1) {

                                item.copyNewQuantity(newQuantity = item.quantity - 1)
                            } else {
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