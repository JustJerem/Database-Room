package com.jeremieguillot.database.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeremieguillot.database.data.ShoppingRepository
import com.jeremieguillot.database.domain.model.ShoppingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@InternalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    private val _items: MutableLiveData<List<ShoppingItem>> = MutableLiveData()
    val items: LiveData<List<ShoppingItem>> get() = _items

    fun getAllShoppingItems() {
        viewModelScope.launch {
            repository.getAllItems().collect {
                _items.value = it
            }
        }
    }

    fun addItem(name: String, quantity: Int) {
        val item = ShoppingItem(UUID.randomUUID().toString(),name, quantity)
        viewModelScope.launch {
            repository.addItem(item)
        }
    }

    fun filterList(filter: String) {
        viewModelScope.launch {
            if (filter.isNotEmpty() && filter.length > 1) {
                repository.filterItems(filter).collect {
                    _items.value = it
                }
            } else {
                getAllShoppingItems()
            }
        }
    }

    fun delete(item: ShoppingItem) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }
}