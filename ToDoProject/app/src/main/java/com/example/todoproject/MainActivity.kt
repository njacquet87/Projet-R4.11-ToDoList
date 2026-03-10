package com.example.todoproject

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.material3.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.ViewModel.TaskViewModelFactory
import com.example.todoproject.ViewModel.UserViewModel
import com.example.todoproject.ViewModel.UserViewModelFactory
import com.example.todoproject.data.repositories.OfflineTaskRepository
import com.example.todoproject.data.TaskDatabase
import com.example.todoproject.data.repositories.OfflineUserRepository
import com.example.todoproject.ui.theme.ToDoProjectTheme
import kotlin.random.Random

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

/**
 * This object is used to show notifications.
 * It contains a single function showNotification that takes the title, the text and the icon as parameters.
 */
object NotificationHelper {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(context: Context, notifTitle: String, notifText: String, icon: Int = android.R.drawable.ic_dialog_info) {

        // Creates an Intent that opens MainActivity when the notification is tapped
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, "task_channel")
            .setSmallIcon(icon)
            .setContentTitle(notifTitle)
            .setContentText(notifText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(Random.nextInt(), notification)
    }
}

class MainActivity : ComponentActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        createNotificationChannel(this)

        val database = TaskDatabase.getDatabase(this)
        val taskRepository = OfflineTaskRepository(database.taskDao())
        val userRepository = OfflineUserRepository(database.UserDao())

        taskViewModel = ViewModelProvider(this, TaskViewModelFactory(taskRepository, userRepository))[TaskViewModel::class.java]
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository))[UserViewModel::class.java]

        setContent {
            ToDoProjectTheme {
                MaterialTheme {
                    AppNavigation(taskViewModel, userViewModel)
                }
            }
        }
    }
}