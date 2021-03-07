package com.jeremieguillot.database.data.local

import androidx.room.*
import com.jeremieguillot.database.domain.model.CourseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseItemDao {
    @Query("SELECT * FROM courseitem ORDER BY name")
    fun getAll(): Flow<List<CourseItem>>

    //@Query("SELECT * FROM courseitem WHERE name LIKE :name") if we wanted to search on full name
    @Query("SELECT * FROM courseitem WHERE name LIKE '%' || :name || '%'")
    fun filterByName(name: String): Flow<List<CourseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg courseItem: CourseItem)

    @Delete
    suspend fun delete(courseItem: CourseItem)
}