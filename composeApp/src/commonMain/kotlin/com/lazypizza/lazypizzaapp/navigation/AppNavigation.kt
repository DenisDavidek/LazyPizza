package com.lazypizza.lazypizzaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.MainProductCatalogRoot
import com.lazypizza.lazypizzaapp.pizza_product.presentation.ProductDetailScreen

@Composable
fun AppNavigation(navHostController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = LazyPizzaScreen.MainProductCatalog,
        modifier = modifier
    ) {
        composable<LazyPizzaScreen.MainProductCatalog> {
            MainProductCatalogRoot(
                onNavigateToProductDetails = {
                    navHostController.navigate(LazyPizzaScreen.ProductDetail)
                }
            )
        }
        composable<LazyPizzaScreen.ProductDetail> {
            ProductDetailScreen(
                onClick = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}