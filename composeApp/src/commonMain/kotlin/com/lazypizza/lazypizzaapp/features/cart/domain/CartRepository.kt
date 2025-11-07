package com.lazypizza.lazypizzaapp.features.cart.domain

import com.lazypizza.lazypizzaapp.features.cart.data.db.ShoppingCartItemEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    val allCartItems: Flow<List<ShoppingCartItemEntity>>

    suspend fun upsertCartItem(item: ShoppingCartItem) // Expects domain model
    suspend fun deleteItem(item: ShoppingCartItem)
    suspend fun clearCart()

}