package com.example.todoproject.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoproject.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // Thanks to the OnConflictStrategy.IGNORE, if the user already exists, the insert operation will be ignored
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM User WHERE id = 1")
    fun getUser(): Flow<UserEntity?>

    @Query("UPDATE User SET nbrOfTaskCompleted = nbrOfTaskCompleted + 1 WHERE id = 1")
    suspend fun incrementTasksCompleted()
}