package com.jeremieguillot.database.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.jeremieguillot.database.data.local.ShoppingItemDao
import com.jeremieguillot.database.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingItemDao,
    private val refOnlineDatabase: DatabaseReference
) {
    fun getAllItems(): Flow<List<ShoppingItem>> {
        return shoppingDao.getAll()
    }

    fun filterItems(filter: String) = shoppingDao.filterByName(filter)

    suspend fun addItem(shoppingItem: ShoppingItem) {
        refOnlineDatabase.child(shoppingItem.uid).setValue(shoppingItem)
        shoppingDao.insert(shoppingItem)
    }

    suspend fun delete(shoppingItem: ShoppingItem) {
        refOnlineDatabase.child(shoppingItem.uid).removeValue()
        shoppingDao.delete(shoppingItem)
    }
}
