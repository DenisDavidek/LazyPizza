package com.lazypizza.lazypizzaapp.features.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lazypizza.lazypizzaapp.core.domain.DefaultInfoItem
import com.lazypizza.lazypizzaapp.design_systems.components.DefaultInfo
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.back_to_menu
import lazypizza.composeapp.generated.resources.head_back_to_menu_and_grab_a_pizza_you_love
import lazypizza.composeapp.generated.resources.your_cart_is_empty
import org.jetbrains.compose.resources.stringResource

@Composable
fun CartScreen(onBackToMenuClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        DefaultInfo(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().align(
                Alignment.Center
            ), defaultInfoItem = DefaultInfoItem(
                title = stringResource(Res.string.your_cart_is_empty),
                message = stringResource(Res.string.head_back_to_menu_and_grab_a_pizza_you_love),
                buttonLabel = stringResource(Res.string.back_to_menu)
            ), onClick = onBackToMenuClick
        )
    }
}