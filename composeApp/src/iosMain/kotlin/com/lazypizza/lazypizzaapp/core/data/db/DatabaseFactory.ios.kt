package com.lazypizza.lazypizzaapp.core.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
      val dbFile = documentDirectory() + "/${AppDatabase.DB_NAME}"
        return Room.databaseBuilder(name = dbFile)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager().URLForDirectory(
            NSCachesDirectory,
            NSUserDomainMask,
            null,
            false,
            null)

        return requireNotNull(documentDirectory?.path)
    }
}