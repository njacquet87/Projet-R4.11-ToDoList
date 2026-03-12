package com.example.todoproject.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoproject.data.entities.TaskEntity
import com.example.todoproject.data.repositories.TaskRepository
import com.example.todoproject.data.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


/**
 * ViewModel for managing tasks in the To_Do application.
 * A ViewModel interacts with the Repository to perform operations on an entity (TaskEntity)
 * and provides data to the UI.
 */
class TaskViewModel(private val repository: TaskRepository, private val userRepository: UserRepository) : ViewModel() {

    val tasks: Flow<List<TaskEntity>> = repository.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getTaskById(id: Int): Flow<TaskEntity?> {
        return repository.getTaskById(id)
    }

    fun addTask(title: String, description: String, date: String, hours: String, periodicity: String, priority: Int, imageUri: String?) {
        viewModelScope.launch {
            repository.insertTask(
                TaskEntity(
                    title = title,
                    description = description,
                    date = date,
                    hours = hours,
                    periodicity = periodicity,
                    priority = priority,
                    imageUri = imageUri,
                    status = "En cours"
                )
            )
        }
    }

    fun updateTask(id: Int, title: String, description: String, date: String, hours: String, periodicity: String, priority: Int, status: String, imageUri: String?) {
        viewModelScope.launch {
            repository.updateTask(
                TaskEntity(
                    id = id,
                    title = title,
                    description = description,
                    date = date,
                    hours = hours,
                    periodicity = periodicity,
                    priority = priority,
                    imageUri = imageUri,
                    status = status
                )
            )
        }
    }

    fun markTaskAsDone(id: Int?) {
        viewModelScope.launch {
            repository.markTaskAsDone(id)
            userRepository.incrementTasksCompleted()
        }
    }

    fun getTasksSortedByStatus(status: String): Flow<List<TaskEntity>> {
        return repository.getTasksSortedByStatus(status)
    }

    fun delete(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
