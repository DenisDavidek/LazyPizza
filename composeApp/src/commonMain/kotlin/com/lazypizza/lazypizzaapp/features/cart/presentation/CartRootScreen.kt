package com.lazypizza.lazypizzaapp.features.cart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import co.touchlab.kermit.Logger
import com.lazypizza.lazypizzaapp.core.domain.DefaultInfoItem
import com.lazypizza.lazypizzaapp.design_systems.components.DefaultInfo
import com.lazypizza.lazypizzaapp.features.product_catalog.presentation.MainProductCatalogViewModel
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.back_to_menu
import lazypizza.composeapp.generated.resources.head_back_to_menu_and_grab_a_pizza_you_love
import lazypizza.composeapp.generated.resources.your_cart_is_empty
import org.jetbrains.compose.resources.stringResource

@Composable
fun CartRootScreen(
    onBackToMenuClick: () -> Unit,
    mainProductCatalogViewModel: MainProductCatalogViewModel,
    viewModel: CartViewModel
) {

    val adaptiveWindow = currentWindowAdaptiveInfo()

    val isExpanded = adaptiveWindow.windowSizeClass
        .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)


    val state by viewModel.cartState.collectAsStateWithLifecycle()

    val mainProductCatalogState by mainProductCatalogViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(mainProductCatalogState) {
        Logger.e("mainProductCatalogState: $mainProductCatalogState size ${mainProductCatalogState.products.size}")

    }

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
                        onAction = { action -> viewModel.onAction(action)})

                    RecommendedAddonsScreen(
                        Modifier.fillMaxSize().weight(1f).background(Color.Yellow)
                    )
                }

            } else {

                Column {

                    CartScreen(
                        state = state,
                        modifier = Modifier.fillMaxSize().weight(1f),
                        onAction = { action -> viewModel.onAction(action)})

                    RecommendedAddonsScreen(
                        Modifier.fillMaxSize().weight(1f).background(Color.Green)
                    )

                }
            }
        }
    }
}