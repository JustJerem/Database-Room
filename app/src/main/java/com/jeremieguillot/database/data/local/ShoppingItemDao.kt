package com.jeremieguillot.database.data.local

import androidx.room.*
import com.jeremieguillot.database.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM shoppingitem ORDER BY name")
    fun getAll(): Flow<List<ShoppingItem>>

    //@Query("SELECT * FROM shoppingitem WHERE name LIKE :name") if we wanted to search on full name
    @Query("SELECT * FROM shoppingitem WHERE name LIKE '%' || :name || '%'")
    fun filterByName(name: String): Flow<List<ShoppingItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: ShoppingItem)

    @Delete
    suspend fun delete(shoppingItem: ShoppingItem)
}