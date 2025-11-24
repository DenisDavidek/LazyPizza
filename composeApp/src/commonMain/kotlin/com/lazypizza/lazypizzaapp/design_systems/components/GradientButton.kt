package com.lazypizza.lazypizzaapp.design_systems.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.dp

@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String,
    colors: List<Color>,
    shadowColor: Color,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier
            .then(
                if(enabled) {
                    Modifier.dropShadow(CircleShape, Shadow(6.dp, shadowColor.copy(alpha = .25f)))
                } else Modifier
            )
            .background(
                brush = if (enabled) {
                    Brush.linearGradient(
                        colors
                    )
                } else SolidColor(Color(0xffebeded)),
                shape = CircleShape
            ),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(.4f)
        ),
        enabled = enabled
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}