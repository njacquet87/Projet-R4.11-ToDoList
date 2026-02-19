package com.example.todoproject.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

/**
 * Reusable function to display a date input field with a date picker dialog
 * @param label the label to display in the input field
 * @param selectedDate the currently selected date, or null if no date is selected
 * @param onDateSelected the action to perform when a date is selected
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInput(label: String, selectedDate: LocalDate?, onDateSelected: (LocalDate) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = selectedDate?.format(formatter) ?: ""

    TextField(value = formattedDate, onValueChange = {}, readOnly = true, label = { Text(label, fontSize = 12.sp) },
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Black)
            }
        },
        colors = TextFieldDefaults.colors(focusedContainerColor = Color.LightGray, unfocusedContainerColor = Color.LightGray,
            focusedLabelColor = Color.Black, unfocusedLabelColor = Color.Black,
            focusedIndicatorColor = Color.Black, unfocusedIndicatorColor = Color.Black,
            cursorColor = Color.Black, focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )

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