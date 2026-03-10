package com.example.todoproject.components.taskComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.components.buttons.IconButtonAction
import com.example.todoproject.data.entities.TaskEntity

/**
 * Reusable function to display a task in the list of tasks on the HomeScreen
 * @param task the task to display
 * @param onDetailClick the action to perform when the detail button is clicked
 * @param onDeleteClick the action to perform when the delete button is clicked
 * @param onUpdateClick the action to perform when the update button is clicked
 * @param viewModel the viewModel to use to mark the task as done when the checkbox is checked
 * @param onTaskDone callback called when the user checks the checkbox to trigger the animation
 * in the HomeScreen so the animation is on all over the screen and not just in the taskItem
 */
@Composable
fun TaskItem(task: TaskEntity, onDetailClick: () -> Unit, onDeleteClick: () -> Unit, onUpdateClick: () -> Unit,
             viewModel: TaskViewModel, onTaskDone: (TaskEntity) -> Unit) {

    val yellow = Color(170, 170, 0, 255)
    val green = Color(0, 170, 0, 255)
    val red = Color(170, 0, 0, 255)

    val color = when (task.status) {
        "En cours" -> yellow
        "Réalisé" -> green
        "En retard" -> red
        else -> Color.Black
    }

    var isChecked by remember(task.status) { mutableStateOf(task.status == "Réalisé") }

    Row(modifier = Modifier.fillMaxWidth().padding(10.dp).clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray).border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

        Column {
            Text(text = task.title, style = MaterialTheme.typography.bodyMedium)
            Text(text = task.status, style = MaterialTheme.typography.bodySmall, color = color)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Checkbox(checked = isChecked, onCheckedChange = {
                    if (!isChecked) {
                        viewModel.markTaskAsDone(task.id)
                        isChecked = true
                        onTaskDone(task)
                    }
                },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0, 170, 0, 255),
                        uncheckedColor = Color.DarkGray, checkmarkColor = Color.Black
                    )
                )

                // Detail
                IconButtonAction(Icons.Filled.Visibility, "Détail de la tâche", onClick = onDetailClick)

                // Update
                IconButtonAction(Icons.Filled.Edit, "Modification de la tâche", onClick = onUpdateClick)

                // Delete
                IconButtonAction(Icons.Filled.Delete, "Suppression de la tâche", onClick = onDeleteClick, color = red)
            }

            Text(text = "Priorité : ${task.priority}", style = MaterialTheme.typography.bodySmall)
        }
    }
}