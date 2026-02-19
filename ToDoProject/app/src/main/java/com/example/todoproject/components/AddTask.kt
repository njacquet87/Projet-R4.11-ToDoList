package com.example.todoproject.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Display a button to add a task and "Ajouter une tache" on the HomeScreen
 * @param onClick the action to perform when the button is clicked
 */
@Composable
fun AddTask(onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

        IconButtonAction(Icons.Filled.AddCircleOutline, "ajout d'une tache",
            onClick = onClick, Modifier.width(35.dp).height(35.dp))

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = "Ajouter une tache", style = MaterialTheme.typography.bodyMedium)
    }
}