package com.lazypizza.lazypizzaapp.features.order_history.data


import com.lazypizza.lazypizzaapp.core.domain.model.User
import com.lazypizza.lazypizzaapp.core.utils.getCurrentTimeStamp
import com.lazypizza.lazypizzaapp.features.order_checkout.presentation.OrderCheckoutState
import com.lazypizza.lazypizzaapp.features.order_history.domain.Order
import com.lazypizza.lazypizzaapp.features.order_history.domain.OrderRepository
import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DefaultOrderRepository(
    val db: FirebaseDatabase,
    ) : OrderRepository {

    override val orders: Flow<List<Order>> =
        db.reference("orders")
            .valueEvents
            .map { snapshot ->
                snapshot.children.map { it.value<Order>() }
            }
            .flowOn(Dispatchers.IO)



     override suspend fun saveOrder(orderCheckoutState: OrderCheckoutState, currentUser: User?, nextOrderId: Int) {
        try {
            val newOrder = Order(id = nextOrderId, userId = currentUser?.phone ?: "",shoppingCartItems = orderCheckoutState.products, created = getCurrentTimeStamp(), pickupTime = orderCheckoutState.earliestPickupTime)
            db.reference("orders").push().setValue(newOrder)

        } catch (e: Exception) {
          e.printStackTrace()
        }

    }

}