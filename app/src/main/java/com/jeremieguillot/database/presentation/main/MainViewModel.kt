package com.jeremieguillot.database.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeremieguillot.database.data.CourseRepository
import com.jeremieguillot.database.domain.model.CourseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@InternalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

    private val _items: MutableLiveData<List<CourseItem>> = MutableLiveData()
    val items: LiveData<List<CourseItem>> get() = _items

    fun getAllCoursesItems() {
        viewModelScope.launch {
            repository.getAllItems().collect {
                _items.value = it
            }
        }
    }

    fun addItem(name: String, quantity: Int) {
        val course = CourseItem(name, quantity)
        viewModelScope.launch {
            repository.addItem(course)
        }
    }

    fun filterList(filter: String) {
        viewModelScope.launch {
            if (filter.isNotEmpty() && filter.length > 1) {
                repository.filterItems(filter).collect {
                    _items.value = it
                }
            } else {
                getAllCoursesItems()
            }
        }
    }

    fun delete(courseItem: CourseItem) {
        viewModelScope.launch {
            repository.delete(courseItem)
        }
    }
}