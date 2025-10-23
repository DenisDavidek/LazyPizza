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
                        // CASE 1: Item is NOT in the cart.
                        // Add the new productToAdd to the list.
                        currentState.items + productToAdd

                    } else {
                        // CASE 2: Item IS ALREADY in the cart.
                        // We need to replace the old item with an updated one.
                        // It's common to increase the quantity here.
                        // Assuming your 'Pizza' data class has a 'quantity' property.
                        currentState.items.map { item ->
                            if (item.id == existingItem.id) {
                                // This is the item we want to update.
                                // Return a copy of it with an increased quantity.
                                item
                              //  item.copy(quantity = item.quantity + 1)
                            } else {
                                // This is a different item, leave it as is.
                                item
                            }
                        }
                    }
                    currentState.copy(items = updatedCartItems)
                }

            }
        }
    }
}