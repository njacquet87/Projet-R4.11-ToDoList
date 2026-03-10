package com.example.todoproject.workers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoproject.NotificationHelper
import com.example.todoproject.data.repositories.OfflineTaskRepository
import com.example.todoproject.data.TaskDatabase
import kotlinx.coroutines.flow.first

suspend fun checkTasksAndNotify(context: Context, repository: OfflineTaskRepository): Boolean {

    // Collect the Flow to get the actual list with .first()
    val tasks = repository.getAllTasks().first()

    return try {
        val hasPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        tasks.forEach { task ->
            if (task.isLate() && task.status == "En cours" && task.periodicity == "Aucune") {
                // Task is late with no periodicity → mark as late
                repository.markTaskAsLate(task.id)
                if (hasPermission) {
                    NotificationHelper.showNotification(context, "Tâche en retard", "${task.title} est en retard", android.R.drawable.ic_dialog_alert)
                }
            } else if (task.isLate() && task.status == "En cours" && task.periodicity != "Aucune") {
                // Task is late with periodicity → change the date and reset to "En cours"
                task.changeDate(task, repository)
                if (hasPermission) {
                    NotificationHelper.showNotification(context, "Date changée", "La date de ${task.title} a été changée", android.R.drawable.ic_dialog_info)
                }
            } else if (task.status == "Réalisé" && task.periodicity != "Aucune") {
                // Task is done with periodicity → change the date and reset to "En cours"
                task.changeDate(task, repository)
                if (hasPermission) {
                    NotificationHelper.showNotification(context, "Nouvelle occurrence", "${task.title} a une nouvelle échéance", android.R.drawable.ic_dialog_info)
                }
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