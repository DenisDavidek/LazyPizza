package com.lazypizza.lazypizzaapp.features.cart.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.lazypizza.lazypizzaapp.features.cart.data.db.ShoppingCartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM shopping_cart_items")
    fun getAllCartItems(): Flow<List<ShoppingCartItemEntity>>

    @Upsert
    suspend fun upsertCartItem(item: ShoppingCartItemEntity)

    @Delete
    suspend fun deleteItem(item: ShoppingCartItemEntity)

    @Query("DELETE FROM shopping_cart_items")
    suspend fun clearCart()
}