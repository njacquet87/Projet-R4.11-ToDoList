package com.example.todoproject.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

/**
 * Reusable function to display a checkbox to show or hide the date and hour input fields.
 * The date and hour input fields are displayed only if the checkbox is checked.
 * The date and hour input fields are used to select the date and hour of the task.
 * @param isChecked the state of the checkbox
 * @param onCheckedChange the action to perform when the state of the checkbox changes
 * @param selectedDate the selected date, can be null if no date is selected
 * @param onDateSelected the action to perform when a date is selected
 * @param time the selected time, can be null if no time is selected
 * @param onTimeSelected the action to perform when a time is selected
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateAndHourInput(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit,
    selectedDate: LocalDate?, onDateSelected: (LocalDate?) -> Unit,
    time: TimePickerState?, onTimeSelected: (TimePickerState?) -> Unit) {

    var checked by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Afficher la sélection", fontSize = 10.sp)

        Checkbox(checked = isChecked, onCheckedChange = { onCheckedChange(it) },
            colors = CheckboxDefaults.colors(checkedColor = Color.LightGray,
                uncheckedColor = Color.LightGray, checkmarkColor = Color.Black)) }

    if (isChecked) {

        Text(
            text = "Cliquez sur les icônes pour sélectionner la date et l'heure. " +
                    "Vous devez avoir rempli la date pour pouvoir remplir l'heure de fin de de la tâche",
            fontSize = 10.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DateInput(onDateSelected = { newDate -> onDateSelected(newDate) })
            TimeSelectInput(
                onConfirm = { newTime -> onTimeSelected(newTime) },
                enabled = selectedDate != null
            )
        }

        Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray, RoundedCornerShape(8.dp)).padding(8.dp)) {
            if (selectedDate != null) {
                Text(text = "Date de fin de tâche : ${selectedDate.toString()}")
            } else {
                Text(text = "Aucune date sélectionnée", fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray, RoundedCornerShape(8.dp)).padding(8.dp)) {
            if (time != null) {
                Text(text = "Heure de fin de tâche : ${time.hour}:${time.minute}")
            } else {
                Text(text = "Aucune heure sélectionnée", fontSize = 13.sp)
            }
        }

        Row() {
            Text(text = "Enlever la date et l'heure de fin de tâche", Modifier.width(125.dp), fontSize = 10.sp)
            Checkbox(
                checked = checked, onCheckedChange = {
                    onDateSelected(null)
                    onTimeSelected(null)
                    checked = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.LightGray,
                    uncheckedColor = Color.LightGray, checkmarkColor = Color.Black
                )
            )
        }

    }
}