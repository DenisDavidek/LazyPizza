package com.lazypizza.lazypizzaapp.design_systems.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.core.domain.model.DefaultInfoItem
import com.lazypizza.lazypizzaapp.design_systems.PrimaryGradientEnd
import com.lazypizza.lazypizzaapp.design_systems.PrimaryGradientStart

@Composable
fun DefaultInfo(modifier: Modifier, defaultInfoItem: DefaultInfoItem, onClick: () -> Unit){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = defaultInfoItem.title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = defaultInfoItem.message,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )


        GradientButton(
            onClick = onClick,
            modifier = Modifier.wrapContentSize().padding(top = 12.dp),
            buttonText = defaultInfoItem.buttonLabel,
            colors = listOf(
                PrimaryGradientStart, PrimaryGradientEnd
            ),
            shadowColor = PrimaryGradientEnd
        )

    }
}
