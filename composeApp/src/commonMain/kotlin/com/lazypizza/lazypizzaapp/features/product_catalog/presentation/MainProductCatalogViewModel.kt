package com.lazypizza.lazypizzaapp.features.product_catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lazypizza.lazypizzaapp.features.pizza_product.ToppingsState
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.ProductCategory
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainProductCatalogViewModel() : ViewModel() {

    private var hasLoadedInitialData = false
    private var database: FirebaseDatabase =
        Firebase.database("https://lazypizza-1999a-default-rtdb.europe-west1.firebasedatabase.app/")
    private val _state = MutableStateFlow(MainProductCatalogState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                getRemoteData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainProductCatalogState()
        )


    private val _toppingsState = MutableStateFlow(ToppingsState())
    val toppingsState = _toppingsState.asStateFlow()


    private val _recommendedAddons = MutableStateFlow<List<Product>>(emptyList())
    val recommendedAddons = _recommendedAddons.asStateFlow()


    private var products: List<Product> = emptyList()


    fun loadToppings(toppings: List<Product>) {
        _toppingsState.value = ToppingsState(toppings = toppings)
    }

    fun clearSelectedToppings() {
        _toppingsState.update { currentState ->

            val currentToppings = currentState.toppings

            // 4. Update the state with the new list where all quantities are 0
            currentState.copy(toppings = currentToppings, selectedToppings = emptyList())
        }

    }

    // 3. The logic to increase quantity. It's the exact same .map logic.
    fun increaseToppingQuantity(topping: Product) {
        _toppingsState.update { currentState ->
            val selectedList = currentState.selectedToppings
            val existingItem = selectedList.find { it.id == topping.id }

            val newSelectedList = if (existingItem == null) {
                // Item is being added for the first time (quantity becomes 1)
                selectedList + topping.copyNewQuantity(1)
            } else {
                // Item already exists, just update its quantity
                selectedList.map {
                    if (it.id == topping.id) it.copyNewQuantity(it.quantity + 1) else it
                }
            }
            currentState.copy(selectedToppings = newSelectedList)
        }
    }


    // 4. The logic to decrease quantity.
    fun decreaseToppingQuantity(topping: Product) {
        _toppingsState.update { currentState ->
            val selectedList = currentState.selectedToppings
            val itemToDecrease = selectedList.find { it.id == topping.id } ?: return@update currentState

            val newSelectedList = if (itemToDecrease.quantity > 1) {
                // Quantity is > 1, so just decrease it
                selectedList.map {
                    if (it.id == topping.id) it.copyNewQuantity(it.quantity - 1) else it
                }
            } else {
                // Quantity is 1, so remove the item from the selected list entirely
                selectedList.filterNot { it.id == topping.id }
            }
            currentState.copy(selectedToppings = newSelectedList)
        }
    }



    private fun getRemoteData() {
        println("Starting Firebase connection test...")
        viewModelScope.launch {
            try {
                println("About to call first()...")

                combine(
                    database.reference("pizzas").valueEvents,
                    database.reference("drinks").valueEvents,
                    database.reference("icecream").valueEvents,
                    database.reference("sauces").valueEvents,
                    database.reference("toppings").valueEvents,

                    ) { pizzas, drinks, icecream, source, toppings ->
                    val drinksList = drinks.children.map {
                        it.value<Product.Drink>()
                    }

                    val pizzasList = pizzas.children.map {
                        it.value<Product.Pizza>()
                    }

                    val iceCreamList = icecream.children.map {
                        it.value<Product.IceCream>()
                    }

                    val saucesList = source.children.map {
                        it.value<Product.Sauce>()
                    }
                    val toppingsList = toppings.children.map {
                        it.value<Product.Topping>()
                    }

                    loadToppings(toppings = toppingsList)

                    _state.update {
                        it.copy(
                            products = pizzasList + drinksList + iceCreamList + saucesList
                        )
                    }

                    products = pizzasList + drinksList + iceCreamList + saucesList


                    val allAddons = saucesList + drinksList

                    if (allAddons.isNotEmpty()) {

                        _recommendedAddons.value = allAddons.shuffled().take(6)
                    }


                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                println("Error: ${e.message}")
                e.printStackTrace()
            }
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

            is MainProductCatalogAction.OnRemoveRecommendedAddon -> {
                val newList = recommendedAddons.value.filter { it.id != action.product.id }
                _recommendedAddons.value = newList
            }
            is MainProductCatalogAction.OnAddRemovedRecommendedAddon -> {
                val newList = recommendedAddons.value + action.product
                _recommendedAddons.value = newList
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