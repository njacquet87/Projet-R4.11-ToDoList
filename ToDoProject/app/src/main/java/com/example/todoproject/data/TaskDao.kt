package com.example.todoproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing tasks in the Room database.
 * This interface defines methods for inserting, updating, deleting,
 * and querying tasks from the database.
 */
@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("SELECT * FROM Tasks ORDER BY priority ASC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM Tasks WHERE id = :id")
    fun getTaskById(id: Int): Flow<TaskEntity?>

    @Query("SELECT * FROM Tasks WHERE status = :status ORDER BY date, hours")
    fun getTasksSortedByStatus(status: String): Flow<List<TaskEntity>>

    @Query("UPDATE Tasks SET status = 'Réalisé' WHERE id = :id")
    suspend fun markAsDone(id: Int?)

    @Query("UPDATE Tasks SET status = 'En retard' WHERE id = :id")
    suspend fun marckAsLate(id: Int?)
}