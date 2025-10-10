package com.lazypizza.lazypizzaapp.features.product_catalog.domain

sealed class Product {
    abstract val id: Int
    abstract val name: String
    abstract val price: Double
    abstract val category: ProductCategory

    data class Pizza(
        override val id: Int,
        override val name: String,
        val ingredients: List<String>,
        override val price: Double,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.PIZZA
    }

    data class Sauce(
        override val id: Int,
        override val name: String,
        override val price: Double,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.SAUCES
    }

    data class IceCream(
        override val id: Int,
        override val name: String,
        override val price: Double,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.ICE_CREAM
    }
}