package com.jeremieguillot.database.data

import com.jeremieguillot.database.data.local.CourseItemDao
import com.jeremieguillot.database.domain.model.CourseItem
import javax.inject.Inject


class CourseRepository @Inject constructor(
    private val courseDao: CourseItemDao
) {
    fun getAllItems() = courseDao.getAll()

    fun filterItems(filter: String) = courseDao.filterByName(filter)

    suspend fun addItem(courseItem: CourseItem) {
        courseDao.insert(courseItem)
    }

    suspend fun delete(courseItem: CourseItem) {
        courseDao.delete(courseItem)
    }
}
