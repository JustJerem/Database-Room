package com.jeremieguillot.database.domain.repository

import com.jeremieguillot.database.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    fun getAllItems(): Flow<List<ShoppingItem>>
    fun filterItems(filter: String): Flow<List<ShoppingItem>>
    suspend fun addItem(shoppingItem: ShoppingItem)
    suspend fun delete(shoppingItem: ShoppingItem)
}