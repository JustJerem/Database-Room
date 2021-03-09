package com.jeremieguillot.database.data

import com.jeremieguillot.database.data.local.ShoppingItemDao
import com.jeremieguillot.database.domain.model.LocalShoppingItem
import com.jeremieguillot.database.domain.model.ShoppingItem
import com.jeremieguillot.database.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingItemDao
) : ShoppingRepository {
    override fun getAllItems(): Flow<List<LocalShoppingItem>> {
        return shoppingDao.getAll()
    }

    override fun filterItems(filter: String) = shoppingDao.filterByName(filter)

    override suspend fun addItem(shoppingItem: ShoppingItem) {
        if (shoppingItem is LocalShoppingItem) {
            shoppingDao.insert(shoppingItem)
        }
    }

    override suspend fun delete(shoppingItem: ShoppingItem) {
        if (shoppingItem is LocalShoppingItem) {
            shoppingDao.delete(shoppingItem)
        }
    }
}
