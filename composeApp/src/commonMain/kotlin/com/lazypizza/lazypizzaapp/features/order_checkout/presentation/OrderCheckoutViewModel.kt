@file:OptIn(ExperimentalTime::class)

package com.lazypizza.lazypizzaapp.features.order_checkout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.features.cart.domain.CartRepository
import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.cart.domain.toDomain
import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.model.PickupTime
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
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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
                            products = products,
                            orderTotal = products.sumOf { cartItem ->
                                cartItem.product.price * cartItem.quantity
                            }
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
                    _state.update {
                        it.copy(
                            selectedOption = action.pickupTime
                        )
                    }

                    if (action.pickupTime == PickupTime.Schedule) {
                        _state.update {
                            it.copy(
                                isPickDateDialogVisible = true
                            )
                        }
                    }
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
                            comment = action.comment
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
                        val updatedItem =
                            existingIdenticalItem.copy(quantity = existingIdenticalItem.quantity + 1)
                        cartRepository.upsertCartItem(updatedItem)
                    }
                }

                is OrderCheckoutAction.OnDatePickerDateSelected -> {
                    _state.update {
                        it.copy(
                            datePickerSelectedDateMillis = action.dateMillis,
                            isPickDateDialogVisible = false,
                            isPickTimeDialogVisible = true,
                        )
                    }
                }

                OrderCheckoutAction.OnDatePickerClose -> {
                    _state.update {
                        it.copy(
                            isPickDateDialogVisible = false,
                            selectedOption = PickupTime.EarlyAvailable
                        )
                    }
                }

                OrderCheckoutAction.OnTimePickerClose -> {
                    _state.update {
                        it.copy(
                            isPickTimeDialogVisible = false,
                            pickTimeError = null,
                            selectedOption = PickupTime.EarlyAvailable
                        )
                    }
                }

                is OrderCheckoutAction.OnTimePickerDateSelected -> {
                    _state.update {
                        val selectedDateMillis = _state.value.datePickerSelectedDateMillis!!
                        val localTz = TimeZone.currentSystemDefault()

                        val dateInstant = Instant.fromEpochMilliseconds(selectedDateMillis)
                        val dateLocal = dateInstant.toLocalDateTime(localTz)

                        val timeMillis = action.timeMillis
                        val hours = (timeMillis / (1000 * 60 * 60)) % 24
                        val minutes = (timeMillis / (1000 * 60)) % 60

                        val combined = LocalDateTime(
                            year = dateLocal.year,
                            monthNumber = dateLocal.monthNumber,
                            dayOfMonth = dateLocal.dayOfMonth,
                            hour = hours.toInt(),
                            minute = minutes.toInt(),
                            second = 0
                        )

                        val combinedMillis = combined.toInstant(localTz).toEpochMilliseconds()

                        val now = Clock.System.now()
                        val nowLocal = now.toLocalDateTime(localTz)
                        val isToday = dateLocal.date == nowLocal.date

                        val selectedTimeInMinutes = hours.toInt() * 60 + minutes.toInt()
                        val storeOpenTime = 10 * 60 + 15
                        val storeCloseTime = 21 * 60 + 45

                        val error = when {
                            selectedTimeInMinutes < storeOpenTime || selectedTimeInMinutes > storeCloseTime -> {
                                "Pickup available between 10:15 and 21:45"
                            }

                            isToday -> {
                                val nowInMinutes = nowLocal.hour * 60 + nowLocal.minute
                                val minimumTime = nowInMinutes + 15

                                if (selectedTimeInMinutes < minimumTime) {
                                    "Pickup is possible at least 15 minutes from the current time"
                                } else {
                                    null
                                }
                            }

                            else -> null
                        }

                        it.copy(
                            earliestPickupTime = if (error == null) getFormattedDateTime(
                                combinedMillis
                            ) else it.earliestPickupTime,
                            isPickTimeDialogVisible = error != null,
                            pickTimeError = error,
                            isPickupTimeValid = error == null,
                            datePickerSelectedDateMillis = if (error == null) null else selectedDateMillis
                        )
                    }
                }

                is OrderCheckoutAction.OnPlaceOrderClick -> {
                    val nextOrderId = action.nextOrderId
                    _state.update {
                        it.copy(
                            isConfirmingOrder = true,
                            orderId = "#$nextOrderId"
                        )
                    }
                }

                else -> {}
            }
        }
    }

}