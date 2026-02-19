package com.example.todoproject.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoproject.components.Header
import com.example.todoproject.components.IconButtonAction
import com.example.todoproject.components.TaskDetail
import com.example.todoproject.mockTasks

/**
 * Display the details of a task
 * @param navController the navController to navigate between screens
 * @param name the name of the user (use in Header)
 * @param firstName the first name of the user (use in Header)
 * @param taskId the id of the task to display. The task is found from the mockTasks list using this id
 */
@Composable
fun DetailScreen(navController: NavController, name: String, firstName: String, taskId : String) {

    // header
    Header(name, firstName)

    Column(Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            // Back Arrow to go back to the HomeScreen
            IconButtonAction(Icons.AutoMirrored.Filled.ArrowBack, "Suppression de la tache",
                onClick = { navController.popBackStack() })

            Text(text = "Detail de la tâche")
        }

        Column(Modifier.width(250.dp).height(500.dp).clip(RoundedCornerShape(14.dp))
                .background(Color.Gray).border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(14.dp)),) {

            // Find the task from the mockTasks list using the taskId
            val task = mockTasks.find { it.id == taskId.toIntOrNull() }

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

                // Update
                IconButtonAction(Icons.Filled.Edit, "Modification de la tache",
                    onClick = {/* TODO */})

                // Delete
                IconButtonAction(Icons.Filled.Delete, "Suppression de la tache",
                    onClick = {/* TODO */})
            }

            // Task details
            Column(modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center) {

                // title
                TaskDetail(task, "Titre : ", task?.title ?: "Tâche non trouvée")

                // description
                TaskDetail(task, "Description : ", task?.description ?: "Tâche non trouvée")

                // date
                TaskDetail(task, "Date : ", task?.date?.toString() ?: "Tâche non trouvée")

                // TODO: add status and button to change the status of the task
            }

        }
    }
}