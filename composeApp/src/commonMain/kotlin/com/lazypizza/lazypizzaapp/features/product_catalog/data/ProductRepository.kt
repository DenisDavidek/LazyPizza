package com.lazypizza.lazypizzaapp.features.product_catalog.data

import dev.gitlive.firebase.database.FirebaseDatabase
import org.koin.core.component.getScopeId

class ProductRepository(
    private val database: FirebaseDatabase
) {

    init {

    }

    suspend fun getAllData() {

    }
}