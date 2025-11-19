@file:OptIn(ExperimentalTime::class)

package com.lazypizza.lazypizzaapp.features.order_checkout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.features.cart.domain.CartRepository
import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.cart.domain.toDomain
import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.utils.getFormattedDateTime
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class OrderCheckoutViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    private var database: FirebaseDatabase =
        Firebase.database("https://lazypizza-1999a-default-rtdb.europe-west1.firebasedatabase.app/")

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(OrderCheckoutState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadAddOns()
                getCurrentTime()
                loadProductDetails()

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = OrderCheckoutState()
        )

    private fun getCurrentTime() {
        viewModelScope.launch {
            val clock = Clock.System.now()
            _state.update {
                it.copy(
                    earliestPickupTime = getFormattedDateTime(
                        dateTimeMillis = clock.toEpochMilliseconds() + 15 * 60 * 1000
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadProductDetails() {
        viewModelScope.launch {
            cartRepository
                .allCartItems
                .mapLatest { items ->
                    items.toDomain()
                }
                .collect { products ->
                    _state.update {
                        it.copy(
                            products = products
                        )
                    }
                }
        }
    }

    private fun loadAddOns() {
        viewModelScope.launch {
            try {
                combine(
                    database.reference("drinks").valueEvents,
                    database.reference("sauces").valueEvents,
                ) { drinks, source ->
                    val drinksList = drinks.children.map {
                        it.value<Product.Drink>()
                    }

                    val saucesList = source.children.map {
                        it.value<Product.Sauce>()
                    }

                    val allAddons = saucesList + drinksList

                    if (allAddons.isNotEmpty()) {
                        _state.update { state ->
                            state.copy(
                                addOns = allAddons.shuffled().take(6)
                            )
                        }
                    }


                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                Logger.e("OrderCheckout Viewmodel") { "Error: ${e.message}" }
            }
        }
    }


    fun onAction(action: OrderCheckoutAction) {
        viewModelScope.launch {
            when (action) {
                is OrderCheckoutAction.OnPickupTimeSelected -> {

                }

                OrderCheckoutAction.OnProductDetailsToggle -> {
                    _state.update {
                        it.copy(
                            isOrderDetailsExpanded = !it.isOrderDetailsExpanded
                        )
                    }
                }

                is OrderCheckoutAction.OnCommentChange -> {
                    _state.update {
                        it.copy(
                            comment = it.comment
                        )
                    }
                }

                is OrderCheckoutAction.OnDeleteProductFromCart -> {
                    cartRepository.deleteItem(action.item)
                }

                is OrderCheckoutAction.OnIncreaseQuantity -> {
                    val itemToIncrease = action.item
                    val updatedItem = itemToIncrease.copy(
                        quantity = itemToIncrease.quantity + 1
                    )

                    cartRepository.upsertCartItem(updatedItem)
                }

                is OrderCheckoutAction.OnDecreaseQuantity -> {
                    val itemToDecrease = action.item
                    if (itemToDecrease.quantity > 1) {
                        val updatedItem = itemToDecrease.copy(
                            quantity = itemToDecrease.quantity - 1
                        )
                        cartRepository.upsertCartItem(updatedItem)
                    } else {
                        cartRepository.deleteItem(itemToDecrease)
                    }
                }


                is OrderCheckoutAction.OnAddOnPlusClick -> {
                    val productToAdd = action.product

                    // Find if an identical product configuration already exists.
                    val existingIdenticalItem = _state.value.products.find {
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
                        cartRepository.upsertCartItem(updatedItem)
                    }
                }
                else -> {}
            }
        }
    }

}