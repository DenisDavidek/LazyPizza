package com.lazypizza.lazypizzaapp.core.presentation.design_systems.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.core.presentation.design_systems.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppTextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        enabled = enabled,
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 9.dp
        ),
        shape = CircleShape,
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Column {
            AppTextButton(text = "Button", onClick = {})
            AppTextButton(text = "Button", onClick = {}, enabled = false)

        }
    }
}
