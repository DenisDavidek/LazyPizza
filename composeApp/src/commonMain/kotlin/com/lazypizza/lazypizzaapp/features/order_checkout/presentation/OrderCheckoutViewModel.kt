@file:OptIn(ExperimentalTime::class)

package com.lazypizza.lazypizzaapp.features.order_checkout.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.core.utils.getFormattedDateTime
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class OrderCheckoutViewModel : ViewModel() {

    private var database: FirebaseDatabase =
        Firebase.database("https://lazypizza-1999a-default-rtdb.europe-west1.firebasedatabase.app/")

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(OrderCheckoutState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadAddOns()
                getCurrentTime()

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
        when (action) {
            is OrderCheckoutAction.OnPickupTimeSelected -> {

            }

            OrderCheckoutAction.OnProductDetailsToggle -> {

            }

            else -> {}
        }
    }

}