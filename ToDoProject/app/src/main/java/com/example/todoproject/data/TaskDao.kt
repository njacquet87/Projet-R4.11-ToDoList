package com.example.todoproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("SELECT * FROM Tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM Tasks WHERE id = :id")
    fun getTaskById(id: Int): Flow<TaskEntity>

    @Query("SELECT * FROM Tasks ORDER BY status ASC")
    fun getTasksSortedByStatus(): Flow<List<TaskEntity>>
}