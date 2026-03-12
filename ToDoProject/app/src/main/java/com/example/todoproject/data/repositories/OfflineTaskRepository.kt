package com.example.todoproject.data.repositories

import com.example.todoproject.data.entities.TaskEntity
import com.example.todoproject.data.repositories.TaskRepository
import com.example.todoproject.data.daos.TaskDao
import kotlinx.coroutines.flow.Flow

/**
 * Repository implementation for managing tasks using Room database.
 * This class provides methods to perform operations on tasks
 * and retrieve tasks in various ways (all tasks, by ID, sorted by status).
 */
class OfflineTaskRepository(private val taskDao: TaskDao) : TaskRepository {
    override fun getAllTasks(): Flow<List<TaskEntity>> = taskDao.getAllTasks()

    override fun getTaskById(id: Int): Flow<TaskEntity?> = taskDao.getTaskById(id)

    override fun getTasksSortedByStatus(status: String): Flow<List<TaskEntity>> = taskDao.getTasksSortedByStatus(status)

    override fun getTasksSortedByDate(): Flow<List<TaskEntity>> = taskDao.getTasksSortedByDate()

    override fun getTasksSortedByAlpha(): Flow<List<TaskEntity>> = taskDao.getTasksSortedByAlpha()

    override suspend fun markTaskAsDone(id: Int?) = taskDao.markAsDone(id)

    override suspend fun markTaskAsLate(id: Int?) = taskDao.marckAsLate(id)

    override suspend fun insertTask(task: TaskEntity) = taskDao.insert(task)

    override suspend fun updateTask(task: TaskEntity) = taskDao.update(task)

    override suspend fun deleteTask(task: TaskEntity) = taskDao.delete(task)
}