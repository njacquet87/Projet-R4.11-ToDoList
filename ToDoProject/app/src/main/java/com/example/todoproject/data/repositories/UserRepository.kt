package com.example.todoproject.data.repositories

import com.example.todoproject.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing user data, including retrieval and updates related to the user's task completion statistics.
 */
interface UserRepository {

    fun getUser(): Flow<UserEntity?>;

    suspend fun initUser()

    suspend fun incrementTasksCompleted();
}
