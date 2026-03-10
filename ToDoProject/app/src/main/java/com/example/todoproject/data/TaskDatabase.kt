package com.example.todoproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoproject.data.daos.TaskDao
import com.example.todoproject.data.daos.UserDao
import com.example.todoproject.data.entities.TaskEntity
import com.example.todoproject.data.entities.UserEntity

/**
 * Room database for managing tasks in the To-Do application.
 * This class defines the database configuration.
 * The version in the @Database annotation should be incremented whenever the database schema is changed.
 */
@Database(entities = [TaskEntity::class, UserEntity::class], version = 5, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun UserDao(): UserDao

    companion object {

        @Volatile
        private var Instance: TaskDatabase? = null

        fun getDatabase(context: Context) : TaskDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TaskDatabase::class.java, "task_database")
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .addMigrations(MIGRATION_3_4)
                    .addMigrations(MIGRATION_4_5)
                    .build()
                    .also { Instance = it }
            }
        }

        // Manual migration from version 1 to version 2: Adding a new column "hours" to the Tasks table.
        val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Tasks ADD COLUMN hours TEXT NOT NULL DEFAULT ''")
            }
        }

        val MIGRATION_2_3 = object : androidx.room.migration.Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Tasks ADD COLUMN periodicity TEXT NOT NULL DEFAULT 'Aucune'")
            }
        }

        val MIGRATION_3_4 = object : androidx.room.migration.Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Tasks ADD COLUMN priority INTEGER NOT NULL DEFAULT 1")
            }
        }

        val MIGRATION_4_5 = object : androidx.room.migration.Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `User` (`id` INTEGER NOT NULL, `nbrOfTaskCompleted` INTEGER NOT NULL DEFAULT 0, PRIMARY KEY(`id`))")
            }
        }
    }
}