package com.example.todoproject.pages

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.ViewModel.UserViewModel
import com.example.todoproject.components.inputs.AppTextField
import com.example.todoproject.components.utils.Header
import com.example.todoproject.components.buttons.IconButtonAction
import com.example.todoproject.components.inputs.DateAndHourInput
import com.example.todoproject.components.inputs.ImagePickerInput
import com.example.todoproject.components.inputs.PeriodicityInput
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.core.net.toUri

/**
 * Convert a string in the format "yyyy-MM-dd" to a LocalDate object
 * @param date the string to convert
 * @return the LocalDate object corresponding to the string, or null if the string is not
 */
fun dateToLocalDate(date: String?): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        LocalDate.parse(date, formatter)
    } catch (e: Exception) {
        null
    }
}

/**
 * Convert a string in the format "hour:minute" to a TimePickerState object
 * @param time the string to convert
 * @return the TimePickerState object corresponding to the string, or null if the string is not in the correct format
 */
@OptIn(ExperimentalMaterial3Api::class)
fun timeToTimePickerState(time: String?): TimePickerState? {
    return try {
        val parts = time?.split(":")
        if (parts != null && parts.size == 2) {
            val hour = parts[0].toInt()
            val minute = parts[1].toInt()
            TimePickerState(hour, minute, is24Hour = true)
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}

/**
 * Update a task in the TaskViewModel and navigate back to the HomeScreen
 * The status of the task is updated depending on the selected date and the current date.
 * Also work if the hours is modifyed without modifying the date, or if the date is modified without modifying the hours.
 * @param viewModel the TaskViewModel to manage the tasks data
 * @param title the title of the task
 * @param description the description of the task
 * @param date the date of the task
 * @param hours the hours of the task
 * @param status the status of the task
 * @param taskId the id of the task to update
 * @param navController the navController to navigate between screens
 */
@OptIn(ExperimentalMaterial3Api::class)
fun updateTask(
    viewModel: TaskViewModel, title: String, description: String, date: LocalDate?,
    hours: TimePickerState?, periodicity: String, priority: Int, status: String, taskId: Int, imageUri: String?, navController: NavController
) {

    if (date != null && hours != null) {
        var now = LocalDateTime.now()
        var dateToCompare = LocalDateTime.of(date.year, date.month, date.dayOfMonth, hours.hour, hours.minute)

        if (status == "En cours" && dateToCompare.isBefore(now)) {
            viewModel.updateTask(taskId, title, description, date.toString(), timeToString(hours), periodicity, priority, "En retard", imageUri)
            navController.navigate("detail/${taskId}")
        } else if (status == "En retard" && dateToCompare.isAfter(now)) {
            viewModel.updateTask(taskId, title, description, date.toString(), timeToString(hours), periodicity, priority, "En cours", imageUri)
            navController.navigate("detail/${taskId}")
        } else {
            viewModel.updateTask(taskId, title, description, date.toString(), timeToString(hours), periodicity, priority, status, imageUri)
            navController.navigate("detail/${taskId}")
        }
    } else {
        viewModel.updateTask(taskId, title, description, date.toString(), timeToString(hours), periodicity, priority, status, imageUri)
        navController.navigate("detail/${taskId}")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(navController: NavController, viewModel: TaskViewModel, taskId : Int, userViewModel: UserViewModel) {

    var isCheckedDateAndHoursInput by remember { mutableStateOf(false) }
    var isCheckedPeriodicityInput by remember { mutableStateOf(false) }

    val task = viewModel.getTaskById(taskId).collectAsState(initial = null).value

    // Pre-fill with existing task values
    var selectedDate: LocalDate? by remember(task) { mutableStateOf(dateToLocalDate(task?.date)) }
    var time: TimePickerState? by remember(task) { mutableStateOf(timeToTimePickerState(task?.hours)) }
    var periodicity: String by remember(task) { mutableStateOf(task?.periodicity ?: "Aucune") }
    var priority: Int by remember(task) { mutableIntStateOf(task?.priority ?: 3) }
    // "null" string from DB must be treated as actual null
    var imageUri: String? by remember(task) { mutableStateOf(if (task?.imageUri == "null") null else task?.imageUri) }

    val priorityOptions = listOf(3, 2, 1)
    var expanded by remember { mutableStateOf(false) }

    // Auto-open sections if the task already has values
    if (task != null && task.date != "null" && !isCheckedDateAndHoursInput) {
        isCheckedDateAndHoursInput = true
    }
    if (task != null && task.periodicity != "Aucune" && !isCheckedPeriodicityInput) {
        isCheckedPeriodicityInput = true
    }

    Header(userViewModel)

    Column(Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            // Back Arrow to go back to the HomeScreen
            IconButtonAction(Icons.AutoMirrored.Filled.ArrowBack, "Retour",
                onClick = { navController.popBackStack() })

            Text(text = "Modification de la tâche")
        }

        if (task != null) {

            // List of tasks.
            // The verticalScroll modifier is used to make the column scrollable
            // when the content length is greater than the height of the column.
            Column(Modifier.width(300.dp).height(550.dp).clip(RoundedCornerShape(14.dp))
                    .background(Color.Gray).border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(14.dp))
                    .padding(16.dp).verticalScroll(rememberScrollState())) {

                Text(text = "Les champs avec * sont obligatoires", fontSize = 10.sp)

                var title by remember { mutableStateOf(task.title) }

                AppTextField(value = title, onValueChange = { newText -> title = newText },
                    inputTitle = "Titre *", label = "Titre de la tâche")

                var description by remember { mutableStateOf(task.description) }

                AppTextField(value = description, onValueChange = { newText -> description = newText },
                    inputTitle = "Description *", label = "Description de la tâche")

                Text(text = "Date et heure de fin de la tâche", style = MaterialTheme.typography.labelLarge)

                DateAndHourInput(isChecked = isCheckedDateAndHoursInput, onCheckedChange = { isCheckedDateAndHoursInput = it },
                    selectedDate = selectedDate, onDateSelected = { selectedDate = it },
                    time = time, onTimeSelected = { time = it })

                Spacer(Modifier.height(16.dp))

                Text(text = "Périodicitée de la tâche", style = MaterialTheme.typography.labelLarge)

                PeriodicityInput(isChecked = isCheckedPeriodicityInput, onCheckedChange = { isCheckedPeriodicityInput = it },
                    periodicity = periodicity, onPeriodicitySelected = { periodicity = it })

                Spacer(Modifier.height(16.dp))

                Text(text = "Photo de la tâche ", style = MaterialTheme.typography.labelLarge)

                ImagePickerInput(imageUri = imageUri?.let { Uri.parse(it) }, onImageSelected = { imageUri = it?.toString() })

                Spacer(Modifier.height(16.dp))

                Text(text = "Priorité de la tâche", style = MaterialTheme.typography.labelLarge)

                Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
                    Column() {
                        Text(text = "1 : Priorité haute", fontSize = 10.sp)
                        Text(text = "2 : Priorité moyenne", fontSize = 10.sp)
                        Text(text = " 3 : Priorité basse", fontSize = 10.sp)
                    }

                    Box() {
                        Button(onClick = { expanded = true }, modifier = Modifier.width(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                                containerColor = Color.LightGray
                            )) {
                            Text(text = priority.toString(), fontSize = 10.sp)
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
                            modifier = Modifier.width(50.dp).background(Color.LightGray)) {
                            priorityOptions.forEach { option ->
                                DropdownMenuItem(text = { Text(text = option.toString(), color = Color.Black,
                                    fontSize = 10.sp, textAlign = TextAlign.Center) },
                                    onClick = {
                                        priority = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Button(onClick = { updateTask(viewModel, title, description, selectedDate, time, periodicity, priority, task.status, taskId, imageUri, navController) },
                        colors = ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Color.LightGray,
                            disabledContainerColor = Color(170, 0, 0, 255)),
                        enabled = areAllInputNotBlank(title, description)) {
                        Text(text = "Modifier", color = Color.Black)
                    }
                }
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth().padding(10.dp).clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Tâche non trouvée")
            }
        }
    }
}