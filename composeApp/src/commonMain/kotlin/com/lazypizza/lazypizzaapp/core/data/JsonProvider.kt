package com.lazypizza.lazypizzaapp.core.data

import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object JsonProvider {
    // This Json object is configured to handle the polymorphic (sealed) nature of Product
     val json = Json {
        serializersModule = SerializersModule {
            polymorphic(Product::class) {
                subclass(Product.Pizza::class)
                // Add other subclasses here if you create them later
            }
        }
    }
}