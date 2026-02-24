package com.example.todoproject.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoproject.data.TaskEntity
import com.example.todoproject.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


/**
 * ViewModel for managing tasks in the To_Do application.
 * A ViewModel interacts with the Repository to perform operations on an entity (TaskEntity)
 * and provides data to the UI.
 */
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val tasks: StateFlow<List<TaskEntity>> = repository.getAllTasks().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun getTaskById(id: Int): StateFlow<TaskEntity>{
        return repository.getTaskById(id).stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TaskEntity(title = "", description = "", date = "", status = ""))
    }

    fun addTask(title: String, description: String, date: String) {
        viewModelScope.launch {
            repository.insertTask(
                TaskEntity(
                    title = title,
                    description = description,
                    date = date,
                    status = "En cours"
                )
            )
        }
    }
}

