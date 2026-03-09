package com.example.todoproject

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoproject.workers.TaskCheckWorker
import java.util.concurrent.TimeUnit

class ToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        scheduleTaskChecker()
        runImmediateCheck()
    }

    // Periodic check every 15 minutes (minimum allowed by Android)
    private fun scheduleTaskChecker() {

        val workRequest = PeriodicWorkRequestBuilder<TaskCheckWorker>(
            15, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "task_check_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    // Immediate check every time the app starts
    private fun runImmediateCheck() {
        val immediateWork = OneTimeWorkRequestBuilder<TaskCheckWorker>().build()
        WorkManager.getInstance(this).enqueue(immediateWork)
    }
}