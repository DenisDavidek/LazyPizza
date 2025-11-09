package com.lazypizza.lazypizzaapp.features.order_history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.features.order_history.domain.OrderItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private val _orderState = MutableStateFlow(OrderState())
    val orderState = _orderState.asStateFlow()

    fun onAction(action: OrderAction) {
        viewModelScope.launch {
            when (action) {
                is OrderAction.OnCreateOrder -> {
                    val products = action.shoppingCartItems
                    Logger.e("products ${products.size}")

                    val newOrder = OrderItem(_orderState.value.orders.lastIndex, products)
                    _orderState.update { currentState ->
                        currentState.copy(
                            orders = listOf(newOrder) + currentState.orders
                        )
                    }
                }
            }
        }
    }
}