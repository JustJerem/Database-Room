package com.jeremieguillot.database.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeremieguillot.database.domain.model.ShoppingItem

@Database(entities = [ShoppingItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
}