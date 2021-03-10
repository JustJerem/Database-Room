package com.jeremieguillot.database.data

import android.util.Log
import com.google.firebase.database.*
import com.jeremieguillot.database.domain.model.FirebaseShoppingItem
import com.jeremieguillot.database.domain.model.ShoppingItem
import com.jeremieguillot.database.domain.repository.ShoppingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


@ExperimentalCoroutinesApi
class FirebaseShoppingRepository @Inject constructor(
    private val refOnlineDatabase: DatabaseReference
) : ShoppingRepository {

    override fun getAllItems(): Flow<List<FirebaseShoppingItem>> = callbackFlow {

        //create an empty list
        val shoppingList: MutableList<FirebaseShoppingItem?> = ArrayList()
        refOnlineDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear the list if it was used previously
                shoppingList.clear()

                //add item to the list
                for (child in snapshot.children) {
                    val item: FirebaseShoppingItem? =
                        child.getValue(FirebaseShoppingItem::class.java)
                    shoppingList.add(item)
                    //send it back over the callback
                    offer(shoppingList)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseShoppingRepo", databaseError.message)
            }
        })

        //close the flow callback
        awaitClose { cancel() }
    }

    override fun filterItems(filter: String): Flow<List<FirebaseShoppingItem>> = callbackFlow {

        //create an empty list
        val shoppingList: MutableList<FirebaseShoppingItem?> = ArrayList()

        //create a query
        val query: Query = refOnlineDatabase.orderByChild("name").startAt(filter)
            .endAt(filter + "\uf8ff") // "\uf8ff" is actually a Java unicode Escape character
        //call your query
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear the list if it was used previously
                shoppingList.clear()
                //add item to the list
                for (child in snapshot.children) {
                    val item: FirebaseShoppingItem? =
                        child.getValue(FirebaseShoppingItem::class.java)
                    shoppingList.add(item)
                    //send it back over the callback
                    offer(shoppingList)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseShoppingRepo", databaseError.message)
            }
        })

        //close the flow callback
        awaitClose { cancel() }
    }

    override suspend fun addItem(shoppingItem: ShoppingItem) {
        shoppingItem.uid?.let { uid -> refOnlineDatabase.child(uid).setValue(shoppingItem) }
    }

    override suspend fun delete(shoppingItem: ShoppingItem) {
        shoppingItem.uid?.let { refOnlineDatabase.child(it).removeValue() }
    }
}
