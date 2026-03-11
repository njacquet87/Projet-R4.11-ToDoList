package com.example.todoproject.data.repositories

import com.example.todoproject.data.daos.UserDao
import com.example.todoproject.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing user data in the To-Do application.
 * This implementation interacts with the local Room database through the UserDao.
 */
class OfflineUserRepository(private val userDao: UserDao) : UserRepository {

    override fun getUser(): Flow<UserEntity?> = userDao.getUser()

    override suspend fun initUser() {
        userDao.insertUser(UserEntity())
    }

    override suspend fun incrementTasksCompleted() {
        userDao.incrementTasksCompleted()
    }

}