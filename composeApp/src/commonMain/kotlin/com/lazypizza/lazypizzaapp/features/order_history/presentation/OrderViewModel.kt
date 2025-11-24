package com.lazypizza.lazypizzaapp.features.order_history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazypizza.lazypizzaapp.features.order_history.domain.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel(val repository: OrderRepository) : ViewModel() {

    private val _orderState = MutableStateFlow(OrderState())
    val orderState = _orderState.asStateFlow()


    init {
        repository.orders.onEach { orders ->
            _orderState.update { currentState ->
                currentState.copy(
                    orders = orders
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: OrderAction) {
        viewModelScope.launch {
            when (action) {
                is OrderAction.OnCreateOrder -> {
                    val orderCheckoutState = action.orderCheckoutState
                    val currentUser = action.currentUser
                    val nextOrderId = action.nextOrderId

                    repository.saveOrder(orderCheckoutState, currentUser, nextOrderId)

                }
            }
        }
    }
}