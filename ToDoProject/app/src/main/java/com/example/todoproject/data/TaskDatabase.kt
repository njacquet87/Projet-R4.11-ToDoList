package com.example.todoproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Room database for managing tasks in the To-Do application.
 * This class defines the database configuration.
 * The version in the @Database annotation should be incremented whenever the database schema is changed.
 */
@Database(entities = [TaskEntity::class], version = 2, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var Instance: TaskDatabase? = null

        fun getDatabase(context: Context) : TaskDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TaskDatabase::class.java, "task_database")
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { Instance = it }
            }
        }

        // Manual migration from version 1 to version 2: Adding a new column "hours" to the Tasks table.
        val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Tasks ADD COLUMN hours TEXT NOT NULL DEFAULT 'null")
            }
        }
    }
}