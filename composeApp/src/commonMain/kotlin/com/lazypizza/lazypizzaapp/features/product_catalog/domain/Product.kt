package com.lazypizza.lazypizzaapp.features.product_catalog.domain

import kotlinx.serialization.Serializable

@Serializable
sealed class Product {
    abstract val id: Int
    abstract val name: String
    abstract val price: Double
    abstract val category: ProductCategory
    abstract val imageUrl: String

    @Serializable
    data class Pizza(
        override val id: Int,
        override val name: String,
        val ingredients: List<String>,
        override val price: Double,
        override val imageUrl: String,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.PIZZA
    }

    @Serializable
    data class Sauce(
        override val id: Int,
        override val name: String,
        override val price: Double,
        override val imageUrl: String,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.SAUCES
    }

    @Serializable
    data class IceCream(
        override val id: Int,
        override val name: String,
        override val price: Double,
        override val imageUrl: String,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.ICE_CREAM
    }

    @Serializable
    data class Drink(
        override val id: Int,
        override val name: String,
        override val price: Double,
        override val imageUrl: String,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.DRINKS
    }
}