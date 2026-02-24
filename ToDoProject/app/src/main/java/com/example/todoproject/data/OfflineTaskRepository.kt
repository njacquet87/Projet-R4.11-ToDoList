package com.example.todoproject.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository implementation for managing tasks using Room database.
 * This class provides methods to perform operations on tasks
 * and retrieve tasks in various ways (all tasks, by ID, sorted by status).
 */
class OfflineTaskRepository(private val taskDao: TaskDao) : TaskRepository {
    override fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    override fun getTaskById(id: Int): Flow<TaskEntity> = taskDao.getTaskById(id)

    override fun getTasksSortedByStatus(): Flow<List<TaskEntity>> = taskDao.getTasksSortedByStatus()

    override suspend fun insertTask(task: TaskEntity) = taskDao.insert(task)

    override suspend fun updateTask(task: TaskEntity) = taskDao.update(task)

    override suspend fun deleteTask(task: TaskEntity) = taskDao.delete(task)
}