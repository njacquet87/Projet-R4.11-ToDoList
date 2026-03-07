package com.example.todoproject

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoproject.workers.TaskCheckWorker

class ToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        scheduleTaskChecker()
    }

    private fun scheduleTaskChecker() {

        val workRequest = OneTimeWorkRequestBuilder<TaskCheckWorker>().build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }
}