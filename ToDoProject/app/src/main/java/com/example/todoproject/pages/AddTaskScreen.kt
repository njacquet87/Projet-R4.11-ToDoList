package com.example.todoproject.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.components.inputs.AppTextField
import com.example.todoproject.components.inputs.DateAndHourInput
import com.example.todoproject.components.utils.Header
import com.example.todoproject.components.buttons.IconButtonAction
import com.example.todoproject.components.inputs.PeriodicityInput
import java.time.LocalDate

/**
 * Check if all the inputs are not blank to enable the "Valider" button
 * The date is not verified because it can be null
 * @param title the title of the task
 * @param description the description of the task
 * @return true if all the inputs are not blank, false otherwise
 */
fun areAllInputNotBlank(title: String, description: String): Boolean {
    return title.isNotBlank() && description.isNotBlank()
}

/**
 * Convert a TimePickerState to a string in the format "hour:minute"
 * @param time the TimePickerState to convert
 * @return the string representation of the time in the format "hour:minute"
 */
@OptIn(ExperimentalMaterial3Api::class)
fun timeToString(time: TimePickerState?): String {
    return if (time != null) {
        "${time.hour}:${time.minute}"
    } else {
        "null"
    }
}

/**
 * Add a task to the list of tasks in the TaskViewModel and navigate back to the HomeScreen
 * @param viewModel the TaskViewModel to manage the tasks data
 * @param title the title of the task
 * @param description the description of the task
 * @param date the date of the task
 * @param navController the navController to navigate between screens
 */
@OptIn(ExperimentalMaterial3Api::class)
fun addTask(viewModel: TaskViewModel, title: String, description: String, date: LocalDate?, hours: TimePickerState?, periodicity: String, navController: NavController) {
    viewModel.addTask(title, description, date.toString(), timeToString(hours), periodicity)
    navController.navigate("home")
}

/**
 * Display the screen to add a task
 * @param navController the navController to navigate between screens
 * @param viewModel the TaskViewModel to manage the tasks data
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController, viewModel: TaskViewModel) {

    var isCheckedDateAndHoursInput by remember { mutableStateOf(false) }
    var isCheckedPeriodicityInput by remember { mutableStateOf(false) }

    var selectedDate: LocalDate? by remember { mutableStateOf(null) }
    var time: TimePickerState? by remember { mutableStateOf(null) }
    var periodicity: String by remember { mutableStateOf("Aucune") }

    // header
    Header()

    Column(Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            // Back Arrow to go back to the HomeScreen
            IconButtonAction(
                Icons.AutoMirrored.Filled.ArrowBack, "Retour",
                onClick = { navController.popBackStack() })

            Text(text = "Ajouter une tâche")
        }

        // The verticalScroll modifier is used to make the column scrollable
        // when the content length is greater than the height of the column.
        Column(Modifier.width(300.dp).height(550.dp).clip(RoundedCornerShape(14.dp))
            .background(Color.Gray).border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(14.dp))
            .padding(16.dp).verticalScroll(rememberScrollState())) {

            Text(text = "Les champs avec * sont obligatoires", fontSize = 10.sp)

            var title by remember { mutableStateOf("") }

            AppTextField(value = title, onValueChange = { newText -> title = newText }, inputTitle = "Titre *", label = "Titre de la tâche")

            var description by remember { mutableStateOf("") }

            AppTextField(value = description, onValueChange = { newText -> description = newText }, inputTitle = "Description *", label = "Description de la tâche")

            Text(text = "Date et heure de fin de la tâche", style = MaterialTheme.typography.labelLarge)

            DateAndHourInput(isChecked = isCheckedDateAndHoursInput, onCheckedChange = { isCheckedDateAndHoursInput = it },
                selectedDate = selectedDate, onDateSelected = { selectedDate = it },
                time = time, onTimeSelected = { time = it })

            Spacer(Modifier.height(16.dp))

            Text(text = "Périodicitée de la tâche", style = MaterialTheme.typography.labelLarge)

            PeriodicityInput(isChecked = isCheckedPeriodicityInput, onCheckedChange = { isCheckedPeriodicityInput = it },
                periodicity, onPeriodicitySelected = { periodicity = it })

            Spacer(Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {addTask(viewModel, title, description, selectedDate, time, periodicity,navController)},
                    colors = ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Color.LightGray,
                        disabledContainerColor = Color(170, 0, 0, 255)
                    ),
                    enabled = areAllInputNotBlank(title, description)) {
                    Text(text = "Valider", color = Color.Black)
                }
            }

        }
    }
}