package com.lazypizza.lazypizzaapp.design_systems.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.timepicker_cancel_button_text
import lazypizza.composeapp.generated.resources.timepicker_confirm_button_text
import lazypizza.composeapp.generated.resources.timepicker_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun AppTimePicker(
    modifier: Modifier = Modifier,
    value: Long? = null,
    error: String? = null,
    onConfirm: (Long) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val totalMinutes = ((value ?: 0L) / 1000 / 60).toInt()
    val hours = (totalMinutes / 60) % 24
    val minutes = totalMinutes % 60


    val timePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = hours,
        initialMinute = minutes
    )

    CompositionLocalProvider(LocalTonalElevationEnabled provides false) {
        TimePickerDialog(
            modifier = modifier.width(264.dp),
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false),
            confirmButton = {
                GradientButton(
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                    onClick = {
                        onConfirm(timePickerState.selectedTimeMillis())
                    },
                    buttonText = stringResource(Res.string.timepicker_confirm_button_text),
                    colors = listOf(
                        Color(0xffF9966F),
                        Color(0xffF36B50),
                    ),
                    shadowColor = MaterialTheme.colorScheme.primary.copy(.25f)
                )
            },
            dismissButton = {
                AppTextButton(
                    onClick = {
                        onDismiss()
                    },
                    text = stringResource(Res.string.timepicker_cancel_button_text),
                )
            },
            shape = RoundedCornerShape(12.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            title = {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp, start = 24.dp),
                    text = stringResource(Res.string.timepicker_title),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimeInput(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background,
                        timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.surface,
                        timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onSurface,
                        timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                error?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall
                            .copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.selectedTimeMillis(): Long {
    return (hour * 60 + minute) * 60 * 1000L
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Column {
            AppTimePicker(
                value = 1111111123000L,
                error = "Pickup available between 10:15 and 21:45"
            )

//            AppTimePicker(
//                error = null
//            )
        }
    }
}
