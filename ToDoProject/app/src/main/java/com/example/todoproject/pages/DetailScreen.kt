package com.example.todoproject.pages

import android.R
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.components.Header
import com.example.todoproject.components.IconButtonAction
import com.example.todoproject.components.TaskDetail
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import com.example.todoproject.data.TaskEntity
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter


fun markAsDone(viewModel: TaskViewModel, task: TaskEntity?) {
    if (task != null && task.status != "Réalisé") {
        viewModel.markTaskAsDone(task.id)
    }
}

/**
 * Display the details of a task
 * @param navController the navController to navigate between screens
 * @param viewModel the TaskViewModel to manage the tasks data
 * @param taskId the id of the task to display. The task is found from the mockTasks list using this id
 */
@Composable
fun DetailScreen(navController: NavController, viewModel: TaskViewModel, taskId : Int) {

    val task = viewModel.getTaskById(taskId).collectAsState(initial = null).value
    var showPopup by remember { mutableStateOf(false) }

    if (showPopup) {
        Popup(alignment = Alignment.Center, onDismissRequest = { showPopup = false }) {
            Column(modifier = Modifier.fillMaxWidth().padding(10.dp).clip(RoundedCornerShape(10.dp))
                .background(Color.Black).border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                .padding(10.dp)){
                    Text(text = "Vous avez réalisé la tache !", color = Color.White, style = MaterialTheme.typography.headlineSmall)
            }
        }

        LaunchedEffect(Unit) {
            delay(2000)
            showPopup = false
            navController.navigate("home")
        }
    }

    // header
    Header()

    Column(Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            // Back Arrow to go back to the HomeScreen
            IconButtonAction(Icons.AutoMirrored.Filled.ArrowBack, "Retour",
                onClick = { navController.popBackStack() })

            Text(text = "Detail de la tâche")
        }

        // The verticalScroll modifier is used to make the column scrollable
        // when the content length is greater than the height of the column.
        Column(Modifier.width(300.dp).height(550.dp).clip(RoundedCornerShape(14.dp))
            .background(Color.Gray).border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(14.dp))
            .verticalScroll(rememberScrollState())) {

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

                // Update
                IconButtonAction(Icons.Filled.Edit, "Modification de la tache",
                    onClick = { navController.navigate("update/${taskId}") })

                // Delete
                IconButtonAction(Icons.Filled.Delete, "Suppression de la tache",
                    onClick = {/* TODO */})
            }

            // Task details

            if (task != null) {
                Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    // title
                    TaskDetail("Titre : ", task.title)

                    // description
                    TaskDetail("Description : ", task.description)

                    // date
                    val date = task.date
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                    TaskDetail("Date : ", date.format(formatter))

                    // hours
                    TaskDetail("Heure : ", task.hours)

                    Button(onClick = {
                        markAsDone(viewModel, task)
                        showPopup = true },
                        colors = ButtonDefaults.buttonColors(contentColor = Color.Black,
                            containerColor = Color(0, 100, 0, 255)), enabled = task.status != "Réalisé") {
                        if (task.status != "Réalisé") {
                            Text(text = "Finir la tâche")
                        } else {
                            Text(text = "Tâche terminée")
                        }
                    }
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp)
                        .clip(RoundedCornerShape(10.dp)).background(Color.LightGray)
                        .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Tâche non trouvée")
                }
            }
        }
    }
}
