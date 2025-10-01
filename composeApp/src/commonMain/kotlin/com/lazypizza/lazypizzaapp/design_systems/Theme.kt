package com.lazypizza.lazypizzaapp.design_systems

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lazypizza.lazypizzaapp.design_systems.components.AppShapes
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.instrumentsans_medium
import lazypizza.composeapp.generated.resources.instrumentsans_regular
import lazypizza.composeapp.generated.resources.instrumentsans_semibold
import org.jetbrains.compose.resources.Font

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


@Composable
fun appTypography(): Typography {


    val regularFontFamily = FontFamily(Font(Res.font.instrumentsans_regular))
    val mediumFontFamily = FontFamily(Font(Res.font.instrumentsans_medium))
    val semiBoldFontFamily = FontFamily(Font(Res.font.instrumentsans_semibold))

    return Typography(
        // Titles
        titleLarge = TextStyle(
            fontFamily = semiBoldFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp
        ),
        titleMedium = TextStyle(
            fontFamily = semiBoldFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 24.sp
        ),
        titleSmall = TextStyle(
            fontFamily = semiBoldFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            lineHeight = 22.sp
        ),

        // Labels
        labelMedium = TextStyle(
            fontFamily = semiBoldFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),

        // Body
        bodyLarge = TextStyle(
            fontFamily = regularFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 22.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = mediumFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp
        ),
        bodySmall = TextStyle(
            fontFamily = regularFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp
        ),
    /*    // custom alias pre Body-3-Medium
        bodySmallMedium = TextStyle(
            fontFamily = mediumFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )*/
        labelSmall = TextStyle(
            fontFamily = regularFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),
    )
}





// AppTheme composable
@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = appTypography(),
        shapes = AppShapes,
        content = content
    )
}