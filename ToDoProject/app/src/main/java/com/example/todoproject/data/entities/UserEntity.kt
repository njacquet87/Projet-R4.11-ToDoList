package com.example.todoproject.data.entities

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * UserEntity represents the user data in the database. It contains the number of tasks completed by the user and the badge associated with it.
 * The badge is determined by the number of tasks completed and can be "Aucun", "Bronze", "Argent", "Or" or "Diamant".
 * The badge color is also determined by the number of tasks completed and can be black, bronze, silver, gold or diamond color.
 */
@Entity(tableName = "User")
data class UserEntity(

    @PrimaryKey
    val id: Int = 1, // only one user

    @ColumnInfo(name = "nbrOfTaskCompleted")
    val nbrOfTaskCompleted: Int = 0,
) {
    // Colors are defined as companion object constants to avoid Room trying to persist them
    companion object {
        val bronze  = Color(205, 127, 50,  255)
        val silver  = Color(192, 192, 192, 255)
        val gold    = Color(255, 193, 7,   255)
        val diamond = Color(76,  232, 255, 255)
    }

    fun getBadge(): String {
        return when {
            nbrOfTaskCompleted >= 200 -> "Diamant"
            nbrOfTaskCompleted >= 100 -> "Or"
            nbrOfTaskCompleted >= 50  -> "Argent"
            nbrOfTaskCompleted >= 20  -> "Bronze"
            else -> "Aucun"
        }
    }

    fun getBadgeColor(): Color {
        return when {
            nbrOfTaskCompleted >= 200 -> diamond
            nbrOfTaskCompleted >= 100 -> gold
            nbrOfTaskCompleted >= 50  -> silver
            nbrOfTaskCompleted >= 20  -> bronze
            else -> Color(0, 0, 0, 255)
        }
    }
}