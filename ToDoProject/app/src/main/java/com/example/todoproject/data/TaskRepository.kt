package com.example.todoproject.data

import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasks(): Flow<List<TaskEntity>>

    fun getTaskById(id: Int): Flow<TaskEntity>

    fun getTasksSortedByStatus(): Flow<List<TaskEntity>>

    suspend fun insertTask(task: TaskEntity)

    suspend fun updateTask(task: TaskEntity)

    suspend fun deleteTask(task: TaskEntity)
}