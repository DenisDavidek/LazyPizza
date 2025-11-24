package com.lazypizza.lazypizzaapp.design_systems.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lazypizza.lazypizzaapp.design_systems.AppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import lazypizza.composeapp.generated.resources.Res
import lazypizza.composeapp.generated.resources.datepicker_cancel_button_text
import lazypizza.composeapp.generated.resources.datepicker_confirm_button_text
import lazypizza.composeapp.generated.resources.datepicker_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

val formatter = LocalDateTime.Format {
    monthName(MonthNames.ENGLISH_FULL)
    char(' ')
    day()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun AppDatePicker(
    modifier: Modifier = Modifier,
    value: Long? = null,
    onConfirm: (Long?) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val todayMillis = today.toEpochDays() * 24 * 60 * 60 * 1000L
    var selectedDateMillis: Long by remember {
        mutableStateOf(
            value?.takeIf { it >= todayMillis } ?: todayMillis
        )
    }

    var formattedDate = remember { derivedStateOf { formatSelectedDate(selectedDateMillis) } }


    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayMillis
            }
        },
        initialSelectedDateMillis = selectedDateMillis
    )

    LaunchedEffect(datePickerState) {
        snapshotFlow { datePickerState.selectedDateMillis }.collectLatest { millis ->
            millis?.let { selectedDateMillis = it }
        }
    }


    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            GradientButton(
                onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
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
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.datepicker_title),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formattedDate.value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
            DatePicker(
                state = datePickerState,
                modifier = Modifier.fillMaxWidth(),
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                title = null,
                headline = null,
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
fun formatSelectedDate(selectedDateMillis: Long): String {
    return Instant
        .fromEpochMilliseconds(selectedDateMillis)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .format(formatter)
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        AppDatePicker()
    }
}
