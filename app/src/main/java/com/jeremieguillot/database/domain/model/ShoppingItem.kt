package com.jeremieguillot.database.domain.model

abstract class ShoppingItem {
    abstract val uid: String?
    abstract val name: String?
    abstract val quantity: Int?
}