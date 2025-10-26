package com.lazypizza.lazypizzaapp.features.product_catalog.domain

import kotlinx.serialization.Serializable

@Serializable
sealed class Product {
    abstract val id: Int
    abstract val name: String
    abstract val price: Double
    abstract val category: ProductCategory
    abstract val imageUrl: String
    abstract val quantity: Int

    abstract fun copyNewQuantity(newQuantity: Int): Product

    @Serializable
    data class Pizza(
        override val id: Int,
        override val name: String,
        val ingredients: List<String>,
        override val price: Double,
        override val imageUrl: String,
        override val quantity: Int = 1,
        val toppings: List<Topping> = emptyList(),
    ) : Product() {
        override val category: ProductCategory = ProductCategory.PIZZA

        override fun copyNewQuantity(newQuantity: Int): Product {
            return this.copy(quantity = newQuantity)
        }
    }


    @Serializable
    data class Sauce(
        override val id: Int,
        override val name: String,
        override val price: Double,
        override val imageUrl: String,
        override val quantity: Int = 1,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.SAUCES

        override fun copyNewQuantity(newQuantity: Int): Product {
            return this.copy(quantity = newQuantity)
        }
    }

    @Serializable
    data class IceCream(
        override val id: Int,
        override val name: String,
        override val price: Double,
        override val imageUrl: String,
        override val quantity: Int = 1,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.ICE_CREAM

        override fun copyNewQuantity(newQuantity: Int): Product {
            return this.copy(quantity = newQuantity)
        }
    }

    @Serializable
    data class Drink(
        override val id: Int,
        override val name: String,
        override val price: Double,
        override val imageUrl: String,
        override val quantity: Int = 1,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.DRINKS

        override fun copyNewQuantity(newQuantity: Int): Product {
            return this.copy(quantity = newQuantity)
        }
    }

    @Serializable
    data class Topping(
        override val id: Int,
        override val name: String,
        override val price: Double,
        override val imageUrl: String,
        override val quantity: Int = 1,
    ) : Product() {
        override val category: ProductCategory = ProductCategory.DRINKS

        override fun copyNewQuantity(newQuantity: Int): Product {
            return this.copy(quantity = newQuantity)
        }
    }

}