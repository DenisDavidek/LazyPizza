package com.lazypizza.lazypizzaapp.design_systems.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    onDateChange: (Long?) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val todayMillis = today.toEpochDays() * 24 * 60 * 60 * 1000L
    var selectedDateMillis by remember { mutableStateOf(todayMillis) }

    val formatter = LocalDateTime.Format {
        monthName(MonthNames.ENGLISH_FULL)
        char(' ')
        day()
    }

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayMillis
            }
        },
        initialSelectedDateMillis = todayMillis
    )

    LaunchedEffect(datePickerState) {
        snapshotFlow { datePickerState.selectedDateMillis }.collectLatest {
            onDateChange(it)
        }
    }


    val formattedDate =
        Instant
            .fromEpochMilliseconds(selectedDateMillis)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .format(formatter)

//    BasicAlertDialog(
//        onDismissRequest = onDismiss
//    ) {
//        Surface(
//            modifier = modifier
//                .widthIn(max = 360.dp)
//                .wrapContentHeight(),
//            shape = RoundedCornerShape(12.dp),
//            tonalElevation = AlertDialogDefaults.TonalElevation
//        ) {
//            Column {
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = "SELECT DATE",
//                    style = MaterialTheme.typography.labelMedium,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant,
//                    modifier = Modifier.padding(horizontal = 16.dp)
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = formattedDate.toString(),
//                    style = MaterialTheme.typography.titleLarge,
//                    color = MaterialTheme.colorScheme.onSurface,
//                    modifier = Modifier.padding(horizontal = 16.dp)
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//        }
//    }


    DatePickerDialog(
        onDismissRequest = { },
        confirmButton = {
            GradientButton(onClick = {
                onDateChange(datePickerState.selectedDateMillis)
            },
                buttonText = "Ok",
                colors = listOf(
                    Color(0xffF9966F),
                    Color(0xffF36B50),
                ),
                shadowColor = MaterialTheme.colorScheme.primary.copy(.25f)
                )
        },
        modifier = modifier,
        dismissButton = {

        },
        shape = RoundedCornerShape(12.dp),
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,

            )
    ) {
        DatePicker(
            state = datePickerState,
            modifier = modifier,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            title = {
                Text(
                    text = "SELECT DATE",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
                )
            },
            headline = {
                Text(
                    text = formattedDate.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            },
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        DatePicker()
    }

}