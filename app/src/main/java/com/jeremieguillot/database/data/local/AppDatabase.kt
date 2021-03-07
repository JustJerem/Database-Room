package com.jeremieguillot.database.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeremieguillot.database.domain.model.CourseItem

@Database(entities = [CourseItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseItemDao
}