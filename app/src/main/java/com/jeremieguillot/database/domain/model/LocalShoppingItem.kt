package com.jeremieguillot.database.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class LocalShoppingItem(
    @PrimaryKey override val uid: String,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "quantity") override val quantity: Int?
) : ShoppingItem()

