package com.lazypizza.lazypizzaapp.features.cart.data

import com.lazypizza.lazypizzaapp.features.cart.data.db.ShoppingCartItemEntity
import com.lazypizza.lazypizzaapp.features.cart.data.db.dao.CartDao
import com.lazypizza.lazypizzaapp.features.cart.domain.CartRepository
import com.lazypizza.lazypizzaapp.features.cart.domain.ShoppingCartItem
import com.lazypizza.lazypizzaapp.features.cart.domain.toEntity
import kotlinx.coroutines.flow.Flow

class DefaultCartRepository(private val cartDao: CartDao) : CartRepository {
    override val allCartItems: Flow<List<ShoppingCartItemEntity>>
        get() = cartDao.getAllCartItems()

    override suspend fun upsertCartItem(item: ShoppingCartItem) {
        // Convert the domain ShoppingCartItem to a ShoppingCartItemEntity
        val entity = item.toEntity() // Using the new mapping function
        cartDao.upsertCartItem(entity)
    }

    override suspend fun deleteItem(item: ShoppingCartItem) {
        val entity = item.toEntity()
        cartDao.deleteItem(entity)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    }


