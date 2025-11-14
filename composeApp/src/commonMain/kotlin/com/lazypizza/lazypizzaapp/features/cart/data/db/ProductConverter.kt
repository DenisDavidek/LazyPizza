package com.lazypizza.lazypizzaapp.features.cart.data.db

import androidx.room.TypeConverter
import com.lazypizza.lazypizzaapp.core.data.utils.JsonProvider
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product


class ProductConverter {
    @TypeConverter
    fun fromProduct(product: Product): String {
        return JsonProvider.json.encodeToString(product)
    }

    @TypeConverter
    fun toProduct(productJson: String): Product {
        return JsonProvider.json.decodeFromString<Product>(productJson)
    }
}