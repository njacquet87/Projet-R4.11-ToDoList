package com.example.todoproject.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.components.AppTextField
import com.example.todoproject.components.DateInput
import com.example.todoproject.components.Header
import com.example.todoproject.components.IconButtonAction
import java.time.LocalDate

/**
 * Display the screen to add a task
 * @param navController the navController to navigate between screens
 * @param viewModel the TaskViewModel to manage the tasks data
 */
@Composable
fun AddTaskScreen(navController: NavController, viewModel: TaskViewModel) {

    // header
    Header()

    Column(Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            // Back Arrow to go back to the HomeScreen
            IconButtonAction(
                Icons.AutoMirrored.Filled.ArrowBack, "Retour",
                onClick = { navController.popBackStack() })

            Text(text = "Ajouter une tâche")
        }

        Column(Modifier.width(250.dp).height(500.dp).clip(RoundedCornerShape(14.dp))
            .background(Color.Gray).border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(14.dp))
            .padding(16.dp)) {

            var title by remember { mutableStateOf("") }

            AppTextField(value = title, onValueChange = { newText -> title = newText }, inputTitle = "Titre *", label = "Titre de la tache")

            var description by remember { mutableStateOf("") }

            AppTextField(value = description, onValueChange = { newText -> description = newText }, inputTitle = "Description *", label = "Description de la tache")

            Text(text = "Date de fin de la tache", style = MaterialTheme.typography.labelLarge)

            var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

            DateInput(label = "Date de la tâche", selectedDate = selectedDate, onDateSelected = { selectedDate = it })

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {/* TODO */ navController.popBackStack()}
                    , colors = ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Color.LightGray)) {
                    Text(text = "Valider", color = Color.Black)
                }
            }

        }
    }
}

