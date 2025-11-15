package com.lazypizza.lazypizzaapp.design_systems.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RadioGroup(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOptionIndex: Int,
    onOptionSelected: (Int) -> Unit,
) {
    CompositionLocalProvider(LocalRippleConfiguration provides RippleConfiguration(MaterialTheme.colorScheme.primary)) {
        Column(
            modifier.selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            options.forEachIndexed { index, text ->
                val isSelected = index == selectedOptionIndex
                val textColor = if (isSelected) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
                        .clip(CircleShape)
                        .selectable(
                            selected = isSelected,
                            onClick = { onOptionSelected(index) },
                            role = Role.RadioButton
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = null
                    )
                    Text(
                        text = text,
                        color = textColor,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun RadioGroupPreview() {
    var selectedIndex by remember { mutableStateOf(0) }
    AppTheme {
        RadioGroup(
            options = listOf("Option 1", "Option 2", "Option 3Option 3Option 3Option 3Option 3Option 3Option 3Option 3Option 3"),
            selectedOptionIndex = selectedIndex,
            onOptionSelected = { index -> selectedIndex = index }
        )
    }
}
