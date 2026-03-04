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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoproject.components.AddTask
import com.example.todoproject.components.Header
import com.example.todoproject.components.TaskItem
import androidx.compose.runtime.collectAsState
import com.example.todoproject.ViewModel.TaskViewModel

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

        // List of tasks.
        // The verticalScroll modifier is used to make the column scrollable
        // when the content length is greater than the height of the column.
        Column(Modifier.width(300.dp).height(550.dp).clip(RoundedCornerShape(14.dp))
            .background(Color.Gray).border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(14.dp))
            .verticalScroll(rememberScrollState())) {

            // Display each task in the mockTasks list with a TaskItem composable.
            if (tasks.isEmpty()) {
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp).clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray).border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                    .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Aucune tâche à afficher")
                }
            } else {
                for (task in tasks) {
                    TaskItem(task, onDetailClick = { navController.navigate("detail/${task.id}") },
                        onDeleteClick = { /* TODO */ },
                        onUpdateClick = { /* TODO */ })
                }
            }
        }
    }
}