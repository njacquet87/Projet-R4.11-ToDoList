package com.example.todoproject.workers

import android.Manifest
import android.R
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoproject.data.OfflineTaskRepository
import com.example.todoproject.data.TaskDatabase
import com.example.todoproject.data.TaskEntity
import kotlin.collections.forEach
import kotlin.random.Random


object NotificationHelper {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(context: Context, title: String) {

        val notification = NotificationCompat.Builder(context, "task_channel")
            .setSmallIcon(R.drawable.ic_dialog_alert)
            .setContentTitle("Tâche en retard")
            .setContentText("$title est en retard")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context)
            .notify(Random.nextInt(), notification)
    }
}

suspend fun checkTasksAndNotify(context: Context, repository: OfflineTaskRepository) : Boolean {

    val tasks = repository.getTasksSortedByStatus("En cours") as List<TaskEntity>

    return try {
        tasks.forEach { task ->
            if (task.isLate()) {
                repository.markTaskAsLate(task.id)
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
                ) {
                    NotificationHelper.showNotification(context, task.title)
                }
            }
        }
        true
    } catch (e: Exception) {
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