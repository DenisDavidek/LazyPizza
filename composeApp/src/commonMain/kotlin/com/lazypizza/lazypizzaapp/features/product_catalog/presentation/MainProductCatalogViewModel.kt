package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.ProductCategory
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSampleDrinks
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSampleIceCreams
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSamplePizzas
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSampleSauces
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainProductCatalogViewModel() : ViewModel() {

    private var hasLoadedInitialData = false
    private var database: FirebaseDatabase = Firebase.database("https://lazypizza-1999a-default-rtdb.europe-west1.firebasedatabase.app/")
    private val _state = MutableStateFlow(MainProductCatalogState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadData()
                getRemoteData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainProductCatalogState()
        )
    private var products: List<Product> = emptyList()


    private fun getRemoteData() {
        println("Starting Firebase connection test...")
        viewModelScope.launch {
            try {
                println("About to call first()...")
                val snapshot = database.reference("pizzas").valueEvents.first()
                println("Got snapshot!")
                println("Value: ${snapshot.value}")
            } catch (e: Exception) {
                println("Error: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val pizzas = getSamplePizzas()
            val drinks = getSampleDrinks()
            val iceCreams = getSampleIceCreams()
            val sauces = getSampleSauces()

            _state.update {
                it.copy(
                    products = pizzas + drinks + iceCreams + sauces
                )
            }

            products = pizzas + drinks + iceCreams + sauces
        }
    }

    fun onAction(action: MainProductCatalogAction) {
        when (action) {
            is MainProductCatalogAction.OnCategorySelected -> {
                handleCategorySelection(action.productCategory)
            }

            is MainProductCatalogAction.OnSearch -> {
                products.filter { product ->
                    product.name.lowercase().contains(action.query.lowercase())
                }.apply {
                    _state.update { it.copy(products = this) }
                }
            }

            MainProductCatalogAction.OnScrollCompleted -> {
                _state.update { it.copy(scrollToIndex = null) }
            }



            else -> {}
        }
    }


    private fun handleCategorySelection(category: ProductCategory) {
        val currentProducts = _state.value.products

        val index = findCategoryIndex(currentProducts, category)

        _state.update {
            it.copy(scrollToIndex = index)
        }
    }

    private fun findCategoryIndex(products: List<Product>, category: ProductCategory): Int {
        val groupedProducts = products.groupBy { it.category }

        var currentIndex = 0
        for ((cat, productList) in groupedProducts.entries) {
            if (cat == category) {
                return currentIndex
            }
            currentIndex += 1 + productList.size
        }

        return 0
    }

}