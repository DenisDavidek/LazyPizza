package com.lazypizza.lazypizzaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.MainProductCatalogRoot
import com.lazypizza.lazypizzaapp.pizza_product.presentation.ProductDetailScreen
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = LazyPizzaScreen.MainProductCatalog,
        modifier = modifier
    ) {
        composable<LazyPizzaScreen.MainProductCatalog> {
            MainProductCatalogRoot(
                onNavigateToProductDetails = { product ->
                    val productJson = Json.encodeToString(product)

                    navHostController.navigate(LazyPizzaScreen.ProductDetail(productJson))
                }
            )
        }
        composable<LazyPizzaScreen.ProductDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<LazyPizzaScreen.ProductDetail>()

            val product = Json.decodeFromString<Product>(args.productJson)

            ProductDetailScreen(
                product = product,
                onClick = {
                    navHostController.navigateUp()
                },
            )
        }
    }
}