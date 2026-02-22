package com.example.todoproject.data

import android.content.Context

class AppContainer(private val context: Context) {
    val taskRepository: TaskRepository by lazy {
        OfflineTaskRepository(TaskDatabase.getDatabase(context).taskDao())
    }
}