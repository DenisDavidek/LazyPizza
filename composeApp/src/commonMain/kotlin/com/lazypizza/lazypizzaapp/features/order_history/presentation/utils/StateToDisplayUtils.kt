package com.lazypizza.lazypizzaapp.features.order_history.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.Success
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.Warning
import com.lazypizza.lazypizzaapp.features.order_history.domain.OrderState
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.order_state_completed
import lazypizza.composeapp.generated.resources.order_state_in_progress
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrderState.toDisplayText(): String{
    return when(this){
        OrderState.IN_PROGRESS -> stringResource(Res.string.order_state_in_progress)
        OrderState.COMPLETED -> stringResource(Res.string.order_state_completed)

    }
}

@Composable
fun OrderState.toDisplayColor(): Color{
    return when(this){
        OrderState.IN_PROGRESS -> Warning
        OrderState.COMPLETED -> Success

    }
}