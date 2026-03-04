package com.example.todoproject.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoproject.data.TaskEntity

/**
 * Reusable function to display a task in the list of tasks on the HomeScreen
 * @param task the task to display
 * @param onDetailClick the action to perform when the detail button is clicked
 * @param onDeleteClick the action to perform when the delete button is clicked
 * @param onUpdateClick the action to perform when the update button is clicked
 */
@Composable
fun TaskItem(task: TaskEntity, onDetailClick: () -> Unit, onDeleteClick: () -> Unit, onUpdateClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(10.dp).clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray).border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

        Column() { Text(text = task.title, style = MaterialTheme.typography.bodyMedium)
            Text(text = task.status, style = MaterialTheme.typography.bodySmall) }

        Row() {
            // use of material icons from the library material-icons-extended

            // Detail
            IconButtonAction(Icons.Filled.Visibility, "Detail de la tache",
                onClick = onDetailClick)

            // Update
            IconButtonAction(Icons.Filled.Edit, "Modification de la tache",
                onClick = onUpdateClick)

            // Delete
            IconButtonAction(Icons.Filled.Delete, "Suppression de la tache",
                onClick = onDeleteClick)
        }
    }
}