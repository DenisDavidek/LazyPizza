package com.lazypizza.lazypizzaapp.design_systems

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.lazypizza.lazypizzaapp.design_systems.components.AppShapes

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = TextOnPrimary,
    background = BG,
    surface = SurfaceHigher,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceHighest,
    onSurfaceVariant = TextSecondary,
    outline = Outline
)


// AppTheme composable
@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    // momentálne definujeme len svetlú schému
    val colors = LightColors

//     typography = Typography(),

    MaterialTheme(
        colorScheme = colors,
        shapes = AppShapes,
        content = content
    )
}