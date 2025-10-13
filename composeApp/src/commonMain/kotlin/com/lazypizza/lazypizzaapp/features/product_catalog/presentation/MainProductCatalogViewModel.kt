package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.ProductCategory
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSampleDrinks
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSampleIceCreams
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSamplePizzas
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSampleSauces
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainProductCatalogViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MainProductCatalogState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadData()

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainProductCatalogState()
        )
    private var products: List<Product> = emptyList()

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