package com.jeremieguillot.database.data.local

import androidx.room.*
import com.jeremieguillot.database.domain.model.LocalShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM localshoppingitem ORDER BY name")
    fun getAll(): Flow<List<LocalShoppingItem>>

    //@Query("SELECT * FROM shoppingitem WHERE name LIKE :name") if we wanted to search on full name
    @Query("SELECT * FROM localshoppingitem WHERE name LIKE '%' || :name || '%'")
    fun filterByName(name: String): Flow<List<LocalShoppingItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: LocalShoppingItem)

    @Delete
    suspend fun delete(shoppingItem: LocalShoppingItem)
}