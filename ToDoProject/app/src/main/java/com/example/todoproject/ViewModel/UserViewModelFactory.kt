package com.example.todoproject.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoproject.data.repositories.UserRepository

/**
 * Factory class for creating instances of UserViewModel with a UserRepository dependency.
 * This factory is used to provide the UserRepository to the UserViewModel when it is created.
 */
class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}