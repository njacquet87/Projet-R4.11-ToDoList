package com.example.todoproject.components.inputs


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import androidx.compose.ui.graphics.Color

/**
 * Reusable function to display a date input field with a date picker dialog
 * @param onDateSelected the action to perform when a date is selected
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInput(onDateSelected: (LocalDate) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    val today = LocalDate.now()
    val todayMillis = today
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    // this state does not allows the user to select a date before today
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayMillis
            }
        }
    )

    IconButton(onClick = { showDialog = true }) {
        Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Black)
    }

    if (showDialog) {
        DatePickerDialog(onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(date)
                    }
                    showDialog = false
                }) {
                    Text("OK", color = Color.Black)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}