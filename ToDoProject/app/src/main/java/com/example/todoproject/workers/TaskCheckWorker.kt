package com.example.todoproject.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoproject.data.TaskDatabase
import kotlin.collections.forEach

class TaskCheckWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val db = TaskDatabase.getDatabase(applicationContext)
        val taskDao = db.taskDao()

        val tasks = taskDao.getTasksSortedByStatus("En cours")

        val now: String = System.currentTimeMillis().toString()

        tasks.forEach { task ->
            if (task.date < now) {
                taskDao.markAsDone(task.id)
            }
        }

        return Result.success()
    }
}