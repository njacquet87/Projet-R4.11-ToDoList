package com.example.todoproject.pages

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.todoproject.MainActivity
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.components.Header
import com.example.todoproject.components.IconButtonAction
import com.example.todoproject.components.TaskDetail
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.todoproject.components.FireworksAnimation
import com.example.todoproject.data.TaskEntity
import kotlinx.coroutines.delay
import java.time.format.DateTimeFormatter
import kotlin.random.Random


object NotificationHelper {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(context: Context, title: String) {

        // Creates an Intent that opens MainActivity when the notification is tapped
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, "task_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Tâche Réalisée !")
            .setContentText("Bravo ! Vous venez de réaliser la tâche : $title")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(Random.nextInt(), notification)
    }
}

/**
 * Mark a task as done by changing its status to "Réalisé"
 * @param viewModel the TaskViewModel to manage the tasks data
 * @param task the task to mark as done.
 */
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
    val context = LocalContext.current
    var showFireworks by remember { mutableStateOf(false) }
    var showDeletePopUp by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
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
                        .background(Color.Gray)
                        .border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(14.dp))
                        .verticalScroll(rememberScrollState())) {

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {

                        // Update
                        IconButtonAction(Icons.Filled.Edit, "Modification de la tache",
                            onClick = { navController.navigate("update/${taskId}") })

                        // Delete
                        IconButtonAction(Icons.Filled.Delete, "Suppression de la tache",
                            onClick = { showDeletePopUp= true }, color = Color(170, 0, 0, 255))
                    }

                    // Task details

                    if (task != null) {
                        Column(modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
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
                                    showPopup = true
                                    showFireworks = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = Color(0, 100, 0, 255)
                                ), enabled = task.status != "Réalisé"
                            ) {
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

        if (showFireworks) {
            FireworksAnimation(Modifier.fillMaxSize(), 3000, 1f)
            FireworksAnimation(Modifier.fillMaxSize(), 5000, 2f)
            FireworksAnimation(Modifier.fillMaxSize(), 2000, 3f)
        }

        if (showPopup) {
            Popup(alignment = Alignment.Center, onDismissRequest = { showPopup = false }) {
                Column(modifier = Modifier.fillMaxWidth().padding(10.dp).clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray).border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                    .padding(10.dp)){
                    Text(text = "Vous avez réalisé cette tache ! Bravo, une de moins !", style = MaterialTheme.typography.headlineSmall)
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        NotificationHelper.showNotification(context, task?.title ?: "Tâche")
                    }
                }
            }

            LaunchedEffect(Unit) {
                delay(5000)
                showFireworks = false
                showPopup = false
                navController.navigate("home")
            }
        }

        if (showDeletePopUp) {
            Popup(alignment = Alignment.Center, onDismissRequest = {showDeletePopUp = false}) {
                Column(modifier = Modifier.fillMaxWidth().padding(10.dp).clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray).border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                    .padding(10.dp)) {
                    Text(text = "Voulez-vous vraiment supprimer cette tâche ?")
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly) {

                        if (task != null) {
                            Button(onClick = { showDeletePopUp = false }, colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                                containerColor = Color.Gray
                            )) {
                                Text(text = "Annuler")
                            }
                            Button(onClick = { showDeletePopUp = false
                                deleteTask(viewModel, task)
                                navController.navigate("home")},
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = Color.Gray
                                )) {
                                Text(text = "Supprimer")
                            }
                        }
                    }
                }
            }
        }
    }
}

