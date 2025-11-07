package com.lazypizza.lazypizzaapp.features.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazypizza.lazypizzaapp.features.cart.domain.CartRepository
import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.cart.domain.toDomain
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()

    private val cartItems = cartRepository.allCartItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    init {

        cartItems.onEach { items ->
            _cartState.update { it.copy(items = items.toDomain()) }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: CartAction) {
        viewModelScope.launch {
            when (action) {
                is CartAction.OnAddToCart -> {
                    val productToAdd = action.product

                    // Find if an identical product configuration already exists.
                    val existingIdenticalItem = cartItems.value.find {
                        it.product.id == productToAdd.id &&
                                (it.product as? Product.Pizza)?.toppings == (productToAdd as? Product.Pizza)?.toppings
                    }

                    if (existingIdenticalItem == null) {

                        val newCartItem = ShoppingCartItem(
                            product = productToAdd,
                            quantity = 1
                        )
                        cartRepository.upsertCartItem(newCartItem)
                    } else {
                        // If it exists, create a new item with the updated quantity and upsert it.
                        val updatedItem = existingIdenticalItem.copy(quantity = existingIdenticalItem.quantity + 1)
                        cartRepository.upsertCartItem(updatedItem.toDomain())
                    }
                }

                is CartAction.OnDeleteProductFromCart -> {
                    cartRepository.deleteItem(action.item)
                }

                is CartAction.OnIncreaseQuantity -> {
                    val itemToIncrease = action.item
                    val updatedItem = itemToIncrease.copy(quantity = itemToIncrease.quantity + 1)

                    cartRepository.upsertCartItem(updatedItem)
                }

                is CartAction.OnDecreaseQuantity -> {
                    val itemToDecrease = action.item
                    if (itemToDecrease.quantity > 1) {

                        val updatedItem = itemToDecrease.copy(quantity = itemToDecrease.quantity - 1)
                        cartRepository.upsertCartItem(updatedItem)
                    } else {
                        cartRepository.deleteItem(itemToDecrease)
                    }
                }
            }
        }
    }
}


