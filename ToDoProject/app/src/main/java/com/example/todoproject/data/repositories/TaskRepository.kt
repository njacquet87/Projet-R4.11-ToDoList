package com.example.todoproject.data.repositories

import com.example.todoproject.data.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing tasks in the application.
 * This interface defines methods for retrieving, inserting, updating,
 * and deleting tasks.
 */
interface TaskRepository {

    fun getAllTasks(): Flow<List<TaskEntity>>

    fun getTaskById(id: Int): Flow<TaskEntity?>

    fun getTasksSortedByStatus(status: String): Flow<List<TaskEntity>>

    suspend fun markTaskAsDone(id: Int?)

    suspend fun markTaskAsLate(id: Int?)

    suspend fun insertTask(task: TaskEntity)

    suspend fun updateTask(task: TaskEntity)

    suspend fun deleteTask(task: TaskEntity)
}