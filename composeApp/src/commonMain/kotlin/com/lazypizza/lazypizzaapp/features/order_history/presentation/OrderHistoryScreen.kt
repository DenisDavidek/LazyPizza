package com.lazypizza.lazypizzaapp.features.order_history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lazypizza.lazypizzaapp.core.domain.model.DefaultInfoItem
import com.lazypizza.lazypizzaapp.design_systems.components.DefaultInfo
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.not_signed_in
import lazypizza.composeapp.generated.resources.please_sign_in_to_view_your_order_history
import lazypizza.composeapp.generated.resources.sign_in
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrderHistoryScreen(onSignInClick: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        DefaultInfo(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().align(
                Alignment.Center
            ), defaultInfoItem = DefaultInfoItem(
                title = stringResource(Res.string.not_signed_in),
                message = stringResource(Res.string.please_sign_in_to_view_your_order_history),
                buttonLabel = stringResource(Res.string.sign_in)
            ), onClick = onSignInClick
        )
    }
}