package com.lazypizza.lazypizzaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.features.cart.presentation.CartRootScreen
import com.lazypizza.lazypizzaapp.features.cart.presentation.CartViewModel
import com.lazypizza.lazypizzaapp.features.order_history.presentation.OrderHistoryScreen
import com.lazypizza.lazypizzaapp.features.pizza_product.presentation.ProductDetailScreen
import com.lazypizza.lazypizzaapp.features.product_catalog.domain.Product
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.MainProductCatalogRoot
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.MainProductCatalogViewModel
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    modifier: Modifier,
    cartViewModel: CartViewModel,
    onShowSnackBar: (product: Product) -> Unit,
) {

    val mainProductCatalogViewModel: MainProductCatalogViewModel = koinViewModel()


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
                },
                viewModel = mainProductCatalogViewModel,
                cartViewModel = cartViewModel,
                onShowSnackBar = { product ->
                    onShowSnackBar(product)
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

        composable<LazyPizzaScreen.OrderHistory> {
            OrderHistoryScreen(onSignInClick = {})
        }

        composable<LazyPizzaScreen.Cart> {
            CartRootScreen(onBackToMenuClick = {
                navHostController.navigate(LazyPizzaScreen.MainProductCatalog)

                Logger.e("Back to menu click")
            }, mainProductCatalogViewModel = mainProductCatalogViewModel, viewModel = cartViewModel)
        }
    }
}