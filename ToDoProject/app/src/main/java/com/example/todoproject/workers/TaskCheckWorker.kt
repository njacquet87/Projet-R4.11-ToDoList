package com.example.todoproject.workers

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoproject.MainActivity
import com.example.todoproject.data.OfflineTaskRepository
import com.example.todoproject.data.TaskDatabase
import kotlinx.coroutines.flow.first
import kotlin.random.Random

object NotificationHelper {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotificationLate(context: Context, title: String) {

        // Creates an Intent that opens MainActivity when the notification is tapped
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, "task_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Tâche en retard")
            .setContentText("$title est en retard")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(Random.nextInt(), notification)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotificationChange(context: Context, title: String) {

        // Creates an Intent that opens MainActivity when the notification is tapped
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, "task_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Date changée")
            .setContentText("La date de $title a été changée")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(Random.nextInt(), notification)
    }
}

suspend fun checkTasksAndNotify(context: Context, repository: OfflineTaskRepository): Boolean {

    // Collect the Flow to get the actual list with .first()
    val tasks = repository.getTasksSortedByStatus("En cours").first()

    return try {
        tasks.forEach { task ->
            if (task.isLate() && task.periodicity == "Aucune" && ContextCompat.checkSelfPermission(context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED) {
                repository.markTaskAsLate(task.id)
                NotificationHelper.showNotificationLate(context, task.title)
            } else if (task.isLate() && task.periodicity != "Aucune" && ContextCompat.checkSelfPermission(context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED) {
                task.changeDate(task, repository)
                NotificationHelper.showNotificationChange(context, task.title)
            }
        }
        true
    } catch (_: Exception) {
        false
    }
}

class TaskCheckWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val db = TaskDatabase.getDatabase(applicationContext)
        val taskDao = db.taskDao()
        val offlineTaskRepository = OfflineTaskRepository(taskDao)

        val success = checkTasksAndNotify(applicationContext, offlineTaskRepository)

        return if (success) {
            Result.success()
        } else {
            Result.retry()
        }
    }
}