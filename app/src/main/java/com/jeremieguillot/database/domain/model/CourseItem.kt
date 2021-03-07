package com.jeremieguillot.database.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseItem(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "quantity") val quantity: Int?,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)