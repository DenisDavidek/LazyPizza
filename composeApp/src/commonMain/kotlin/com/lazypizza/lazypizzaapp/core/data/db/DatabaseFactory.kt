package com.lazypizza.lazypizzaapp.core.data.db

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create() : RoomDatabase.Builder<AppDatabase>
}