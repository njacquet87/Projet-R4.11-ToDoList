package com.example.todoproject.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.util.Calendar

/**
 * Reusable function to display a time input field with a time picker dialog
 * @param onConfirm the action to perform when a time is selected and confirmed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectInput(onConfirm: (TimePickerState) -> Unit, enabled: Boolean = true) {

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE), is24Hour = true,)

    var showTimePicker by remember { mutableStateOf(false) }

    IconButtonAction(imageVector = Icons.Filled.AccessTime,
        contentDescripton = "Sélectionner une heure", onClick = { showTimePicker = true }, enabled = enabled)

    if (showTimePicker) {
        AlertDialog(onDismissRequest = { showTimePicker = false }, title = { Text("Sélectionner une heure") },
            text = { TimeInput(state = timePickerState) },
            confirmButton = { Button(onClick = { onConfirm(timePickerState)
                    showTimePicker = false }) {
                    Text("OK")
                }
            },
            dismissButton = { Button(onClick = { showTimePicker = false }) {
                    Text("Annuler")
                }
            }
        )
    }
}