package com.example.todoproject.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoproject.data.repositories.TaskRepository
import com.example.todoproject.data.repositories.UserRepository

/**
 * Factory class for creating instances of TaskViewModel with a TaskRepository dependency.
 * This factory is used to provide the necessary repository to the ViewModel when it is created.
 */
class TaskViewModelFactory(private val repository: TaskRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(repository, userRepository) as T
    }
}