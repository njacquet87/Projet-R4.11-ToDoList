package com.example.todoproject

import java.time.LocalDate

// Mock Class for Task
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val date : LocalDate,
    val status: String
)