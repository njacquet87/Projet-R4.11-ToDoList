package com.example.todoproject.pages

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.todoproject.components.AddTask
import com.example.todoproject.components.Header
import com.example.todoproject.components.TaskItem
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.data.TaskEntity
import kotlin.collections.emptyList


/**
 * Function to request the POST_NOTIFICATIONS permission from the user.
 * The permission is requested using the rememberLauncherForActivityResult and ActivityResultContracts.RequestPermission libraries.
 * The function is called in the HomeScreen composable to ensure that the permission is requested when the user opens the app.
 */
@Composable
fun RequestNotificationPermission() {

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) { }
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}

/**
 * Function to delete a task from the database using the TaskViewModel.
 * The function is called when the user clicks on the delete button of a task in the TaskItem composable.
 * @param viewModel the TaskViewModel to manage the tasks data
 * @param task the task to delete
 */
fun deleteTask(viewModel: TaskViewModel, task: TaskEntity) {
    viewModel.delete(task)
}

/**
 * Display the home screen with a header and a list of tasks.
 * Also display a button to add a new task.
 * @param navController the navController to navigate between screens
 * @param viewModel the TaskViewModel to manage the tasks data
 */
@Composable
fun HomeScreen(navController: NavController, viewModel: TaskViewModel) {

    RequestNotificationPermission()
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Toutes") }
    var showDeletePopUp by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<TaskEntity?>(null) }

    // Use collectAsState to observe the tasks flow from the viewModel and update the UI when the data changes.
    // The when statement is used to filter the tasks based on the selected filter.
    val tasks by when (selectedFilter) {
        "Toutes" -> viewModel.tasks.collectAsState(initial = emptyList())
        else -> viewModel.getTasksSortedByStatus(selectedFilter).collectAsState(initial = emptyList())
    }

    val filterOptions = listOf("Toutes", "En cours", "Réalisé", "En retard")

    Box(Modifier.fillMaxSize()) {
        //header
        Header()

        // body
        Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(Modifier.height(80.dp))

            AddTask(onClick = { navController.navigate("add") })

            // List of tasks.
            // The verticalScroll modifier is used to make the column scrollable
            // when the content length is greater than the height of the column.
            Column(Modifier.width(300.dp).height(550.dp).clip(RoundedCornerShape(14.dp))
                    .background(Color.Gray)
                    .border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(14.dp))
                    .verticalScroll(rememberScrollState())) {

                // Display each task in the mockTasks list with a TaskItem composable.
                if (tasks.isEmpty()) {

                    Row(modifier = Modifier.fillMaxWidth().padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Aucune tâche à afficher")
                    }
                } else {
                    for (task in tasks) {
                        TaskItem(
                            task, onDetailClick = { navController.navigate("detail/${task.id}") },
                            onDeleteClick = { showDeletePopUp = true
                                            taskToDelete = task},
                            onUpdateClick = { navController.navigate("update/${task.id}") })
                    }
                }
            }

            if (showDeletePopUp) {
                Popup(alignment = Alignment.Center, onDismissRequest = {showDeletePopUp = false}) {
                    Column(modifier = Modifier.background(Color.White).clip(RoundedCornerShape(10.dp))
                        .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                        .padding(16.dp), verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Voulez-vous vraiment supprimer cette tâche ?")
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly) {

                            if (taskToDelete != null) {
                                Button(onClick = { showDeletePopUp = false }, colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = Color.LightGray
                                )) {
                                    Text(text = "Annuler")
                                }
                                Button(onClick = { showDeletePopUp = false
                                                 deleteTask(viewModel, taskToDelete!!)},
                                    colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = Color.LightGray
                                )) {
                                    Text(text = "Supprimer")
                                }
                            }
                        }
                    }
                }
            }

            Box() {
                Button(
                    onClick = { expanded = true }, modifier = Modifier.width(150.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        containerColor = Color.LightGray
                    )
                ) {
                    Text(text = "Trier par: $selectedFilter", fontSize = 10.sp)
                }
                DropdownMenu(
                    expanded = expanded, onDismissRequest = { expanded = false },
                    modifier = Modifier.width(150.dp).background(Color.LightGray)
                ) {
                    filterOptions.forEach { filter ->
                        DropdownMenuItem(
                            text = { Text(text = filter, color = Color.Black) },
                            onClick = {
                                selectedFilter = filter
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}