package com.lazypizza.lazypizzaapp.design_systems.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.datepicker_cancel_button_text
import lazypizza.composeapp.generated.resources.datepicker_confirm_button_text
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun AppTimePicker(
    modifier: Modifier = Modifier,
    value: Long? = null,
    onConfirm: (Long) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val totalMinutes = ((value ?: 0L) / 1000 / 60).toInt()
    val hours = (totalMinutes / 60) % 24
    val minutes = totalMinutes % 60


    val timePickerState = rememberTimePickerState(
        is24Hour = true,
        initialHour = hours,
        initialMinute = minutes
    )

    val selectedTimeMillis = remember {
        derivedStateOf {
            (timePickerState.hour * 60 + timePickerState.minute) * 60 * 1000L
        }
    }

    TimePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            GradientButton(
                onClick = {
                    onConfirm(timePickerState.selectedTimeMillis())
                },
                buttonText = stringResource(Res.string.datepicker_confirm_button_text),
                colors = listOf(
                    Color(0xffF9966F),
                    Color(0xffF36B50),
                ),
                shadowColor = MaterialTheme.colorScheme.primary.copy(.25f)
            )
        },
        modifier = modifier.widthIn(max = 360.dp),
        dismissButton = {
            AppTextButton(
                onClick = {
                    onDismiss()
                },
                text = stringResource(Res.string.datepicker_cancel_button_text),
            )
        },
        shape = RoundedCornerShape(12.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        title = {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Select time",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
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
        AppTimePicker()
    }
}
