package com.example.todoproject.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoproject.data.repositories.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for managing user-related data and operations.
 * This class will handle user authentication, profile management,
 * and any other user-specific logic needed in the application.
 */
class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val user = repository.getUser().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )

    init {
        viewModelScope.launch {
            repository.initUser()
        }
    }

    suspend fun incrementTasksCompleted() {
        repository.incrementTasksCompleted()
    }
}