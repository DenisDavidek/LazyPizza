package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSampleIceCreams
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSamplePizzas
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.getSauces
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

    private fun loadData() {
        viewModelScope.launch {
            val pizzas = getSamplePizzas()
            val iceCreams = getSampleIceCreams()
            val sauces = getSauces()

            _state.update { it.copy(
                products = pizzas + iceCreams + sauces
            ) }
        }
    }

    fun onAction(action: MainProductCatalogAction) {
        when (action) {
            is MainProductCatalogAction.OnCategorySelected -> {
                if (_state.value.selectedCategory != action.productCategory) {
                    _state.update {
                        it.copy(selectedCategory = action.productCategory)
                    }
                } else {
                    _state.update { it.copy(selectedCategory = null) }
                }
            }

            else -> { }
        }
    }

}