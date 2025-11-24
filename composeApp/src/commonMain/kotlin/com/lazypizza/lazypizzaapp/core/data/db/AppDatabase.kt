package com.lazypizza.lazypizzaapp.core.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lazypizza.lazypizzaapp.features.cart.data.db.ProductConverter
import com.lazypizza.lazypizzaapp.features.cart.data.db.ShoppingCartItemEntity
import com.lazypizza.lazypizzaapp.features.cart.data.db.dao.CartDao

@Database(entities = [ShoppingCartItemEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(ProductConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val cartDao: CartDao

    companion object {
        const val DB_NAME= "lazypizza.db"
    }
}