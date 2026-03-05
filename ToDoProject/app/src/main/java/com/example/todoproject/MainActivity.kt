package com.example.todoproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.lifecycle.ViewModelProvider
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.ViewModel.TaskViewModelFactory
import com.example.todoproject.data.OfflineTaskRepository
import com.example.todoproject.data.TaskDatabase
import com.example.todoproject.ui.theme.ToDoProjectTheme

/**
 * This function is used to create a notification chanel that can be used to send notifications
 */
fun createNotificationChannel(context: Context) {
    val channel = NotificationChannel(
        "task_channel",
        "Task Notifications",
        NotificationManager.IMPORTANCE_HIGH
    )

    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannel(channel)
}

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        createNotificationChannel(this)

        val database = TaskDatabase.getDatabase(this)
        val repository = OfflineTaskRepository(database.taskDao())

        viewModel = ViewModelProvider(this, TaskViewModelFactory(repository))[TaskViewModel::class.java]

        setContent {
            ToDoProjectTheme {
                MaterialTheme {
                    AppNavigation(viewModel)
                }
            }
        }
    }
}