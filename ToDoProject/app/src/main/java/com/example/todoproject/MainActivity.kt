package com.example.todoproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import com.example.todoproject.ui.theme.ToDoProjectTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoProjectTheme {
                MaterialTheme {
                    AppNavigation()
                }
            }
        }
    }
}

const val APP_TITLE = "// TODO"

// Mock data for tasks — replaced map with a list of Task
val mockTasks = mutableListOf(
    Task(1, "Titre 1", "Description de la tâche 1", LocalDate.of(2026, 2, 17), "en cours"),
    Task(2, "Titre 2", "Description de la tâche 2", LocalDate.of(2026, 2, 25), "terminé"),
    Task(3, "Titre 3", "Description de la tâche 3", LocalDate.of(2026, 1, 10), "dépassée")
)