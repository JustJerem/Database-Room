package com.jeremieguillot.database.domain.model

class FirebaseShoppingItem(
    override val uid: String? = null,
    override val name: String? = null,
    override val quantity: Int? = null
) : ShoppingItem()

