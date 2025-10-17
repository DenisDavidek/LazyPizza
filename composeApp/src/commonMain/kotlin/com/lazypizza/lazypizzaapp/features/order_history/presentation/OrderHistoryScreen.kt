package com.lazypizza.lazypizzaapp.features.order_history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.design_systems.PrimaryGradientEnd
import com.lazypizza.lazypizzaapp.design_systems.PrimaryGradientStart
import com.lazypizza.lazypizzaapp.design_systems.components.GradientButton
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.not_signed_in
import lazypizza.composeapp.generated.resources.please_sign_in_to_view_your_order_history
import lazypizza.composeapp.generated.resources.sign_in
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrderHistoryScreen(onSignInClick: () -> Unit) {


    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().align(
                Alignment.Center
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.not_signed_in),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = stringResource(Res.string.please_sign_in_to_view_your_order_history),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )


            GradientButton(
                onClick = onSignInClick,
                modifier = Modifier.wrapContentSize().padding(top = 12.dp),
                buttonText = stringResource(Res.string.sign_in),
                colors = listOf(
                    PrimaryGradientStart, PrimaryGradientEnd
                ),
                shadowColor = PrimaryGradientEnd
            )

        }
    }
}