package com.lazypizza.lazypizzaapp.features.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.core.domain.model.DefaultInfoItem
import com.lazypizza.lazypizzaapp.core.utils.countOverallPrice
import com.lazypizza.lazypizzaapp.design_systems.components.DefaultInfo
import com.lazypizza.lazypizzaapp.features.order_history.presentation.OrderAction
import com.lazypizza.lazypizzaapp.features.order_history.presentation.OrderViewModel
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.MainProductCatalogAction
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.MainProductCatalogViewModel
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.back_to_menu
import lazypizza.composeapp.generated.resources.head_back_to_menu_and_grab_a_pizza_you_love
import lazypizza.composeapp.generated.resources.your_cart_is_empty
import org.jetbrains.compose.resources.stringResource

@Composable
fun CartRootScreen(
    onBackToMenuClick: () -> Unit,
    onNavigateToCheckout: () -> Unit,
    mainProductCatalogViewModel: MainProductCatalogViewModel,
    orderViewModel: OrderViewModel,
    viewModel: CartViewModel,
) {

    val adaptiveWindow = currentWindowAdaptiveInfo()

    val isExpanded = adaptiveWindow.windowSizeClass
        .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)


    val state by viewModel.cartState.collectAsStateWithLifecycle()

    val mainProductCatalogState by mainProductCatalogViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(mainProductCatalogState) {
        Logger.e("mainProductCatalogState: $mainProductCatalogState size ${mainProductCatalogState.products.size}")

    }

    val recommendedAddons by mainProductCatalogViewModel.recommendedAddons.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {


        if (state.items.isEmpty()) {

            DefaultInfo(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().align(
                    Alignment.Center
                ), defaultInfoItem = DefaultInfoItem(
                    title = stringResource(Res.string.your_cart_is_empty),
                    message = stringResource(Res.string.head_back_to_menu_and_grab_a_pizza_you_love),
                    buttonLabel = stringResource(Res.string.back_to_menu)
                ), onClick = onBackToMenuClick
            )

        } else {

            if (isExpanded) {

                Row(modifier = Modifier.fillMaxSize()) {

                    CartScreen(
                        state = state,
                        modifier = Modifier.fillMaxSize().weight(1f),
                        onAction = { action -> viewModel.onAction(action) },
                        onMainProductCatalogAction = { action ->
                            mainProductCatalogViewModel.onAction(action)
                        })

                    RecommendedAddonsScreen(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight().weight(1f)
                            .background(
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(16.dp)
                            ),
                        products = recommendedAddons,
                        totalPrice = state.items.countOverallPrice(),
                        onProductAddClick = { product ->
                            viewModel.onAction(CartAction.OnAddToCart(product))
                            mainProductCatalogViewModel.onAction(
                                MainProductCatalogAction.OnRemoveRecommendedAddon(
                                    product
                                )
                            )
                        },
                        onProceedToCheckoutClick = {
                            onBackToMenuClick()
                        }
                    )
                }

            } else {

                Column {

                    CartScreen(
                        state = state,
                        modifier = Modifier.fillMaxSize().weight(0.75f),
                        onAction = { action -> viewModel.onAction(action) },
                        onMainProductCatalogAction = { action ->
                            mainProductCatalogViewModel.onAction(action)
                        })

                    RecommendedAddonsScreen(
                        modifier = Modifier.fillMaxSize().weight(1.25f),
                        products = recommendedAddons,
                        totalPrice = state.items.countOverallPrice(),
                        onProductAddClick = { product ->
                            viewModel.onAction(CartAction.OnAddToCart(product))
                            mainProductCatalogViewModel.onAction(
                                MainProductCatalogAction.OnRemoveRecommendedAddon(
                                    product
                                )
                            )

                        },
                        onProceedToCheckoutClick = {
//                            orderViewModel.onAction(OrderAction.OnCreateOrder(shoppingCartItems = state.items))

                            onNavigateToCheckout()
                        }
                    )

                }
            }
        }
    }
}