package com.lazypizza.lazypizzaapp.features.order_history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.lazypizza.lazypizzaapp.core.domain.model.DefaultInfoItem
import com.lazypizza.lazypizzaapp.core.presentation.locals.LocalUser
import com.lazypizza.lazypizzaapp.core.utils.isPortrait
import com.lazypizza.lazypizzaapp.design_systems.components.DefaultInfo
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.go_to_menu
import lazypizza.composeapp.generated.resources.no_orders_yet
import lazypizza.composeapp.generated.resources.not_signed_in
import lazypizza.composeapp.generated.resources.please_sign_in_to_view_your_order_history
import lazypizza.composeapp.generated.resources.sign_in
import lazypizza.composeapp.generated.resources.your_orders_will_appear_here_after_your_first_purchase
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrderHistoryRootScreen(
    orderViewModel: OrderViewModel,
    onSignInClick: () -> Unit,
    onGoToMenuClick: () -> Unit
) {

    val adaptiveWindow = currentWindowAdaptiveInfo()

    val isExpanded = adaptiveWindow.windowSizeClass
        .isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)

    val isPortrait = isPortrait()


    val user = LocalUser.current
    val isUserLoggedIn = user != null

    val orderState by orderViewModel.orderState.collectAsStateWithLifecycle()


    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        if (!isUserLoggedIn) {
            DefaultInfo(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().align(
                    Alignment.Center
                ), defaultInfoItem = DefaultInfoItem(
                    title = stringResource(Res.string.not_signed_in),
                    message = stringResource(Res.string.please_sign_in_to_view_your_order_history),
                    buttonLabel = stringResource(Res.string.sign_in)
                ), onClick = onSignInClick
            )
        } else if (orderState.orders.isEmpty()) {

            DefaultInfo(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().align(
                    Alignment.Center
                ), defaultInfoItem = DefaultInfoItem(
                    title = stringResource(Res.string.no_orders_yet),
                    message = stringResource(Res.string.your_orders_will_appear_here_after_your_first_purchase),
                    buttonLabel = stringResource(Res.string.go_to_menu)
                ), onClick = onGoToMenuClick
            )
        } else {

            OrderHistoryScreen(
                state = orderState,
                isExpanded = isExpanded,
                isPortrait = isPortrait,
                modifier = Modifier.fillMaxSize()
            )


        }

    }

}