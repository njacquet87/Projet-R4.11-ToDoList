package com.example.todoproject.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.components.AddTask
import com.example.todoproject.components.Header
import com.example.todoproject.components.TaskItem
import androidx.compose.runtime.collectAsState

/**
 * Display the home screen with a header and a list of tasks.
 * Also display a button to add a new task.
 * @param navController the navController to navigate between screens
 * @param viewModel the TaskViewModel to manage the tasks data
 */
@Composable
fun HomeScreen(navController: NavController, viewModel: TaskViewModel) {

    val tasks = viewModel.tasks.collectAsState().value

    //header
    Header()

    // body
    Column(Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        AddTask(onClick = { navController.navigate("add") })

        Spacer(modifier = Modifier.height(16.dp))

        // List of tasks
        Column(Modifier.width(250.dp).height(500.dp).clip(RoundedCornerShape(14.dp))
            .background(Color.Gray).border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(14.dp))) {

            // Display each task in the mockTasks list with a TaskItem composable.
            if (tasks.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Aucune tâche à afficher")
            } else {
                for (task in tasks) {
                    TaskItem(
                        task, onDetailClick = { navController.navigate("detail/${task.id}") },
                        onDeleteClick = { /* TODO */ },
                        onUpdateClick = { /* TODO */ })
                }
            }
        }
    }
}