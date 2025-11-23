package com.lazypizza.lazypizzaapp.features.order_history.domain

import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    var id: Int,
    var userId: String,
    val shoppingCartItems: List<ShoppingCartItem>,
    val orderState: OrderState = OrderState.IN_PROGRESS,
    val created: Long,
    val pickupTime: String
) {

    fun getOverallPrice(): Double {

        return shoppingCartItems.sumOf { cartItem ->

            var singleItemPrice = cartItem.product.price

            if (cartItem.product is Product.Pizza) {
                // Add the price of all toppings for that pizza.
                singleItemPrice += cartItem.product.toppings.sumOf { topping ->
                    topping.price * topping.quantity
                }
            }

            // Multiply the final price of one item by its quantity in the cart.
            singleItemPrice * cartItem.quantity
        }
    }

}

