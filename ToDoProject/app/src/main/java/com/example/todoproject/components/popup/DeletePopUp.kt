package com.example.todoproject.components.popup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

/**
 * Reusable confirmation popup displayed when the user wants to delete a task.
 * @param onDismiss called when the user dismisses the popup (click outside or "Annuler")
 * @param onConfirm called when the user confirms the deletion
 */
@Composable
fun DeletePopUp(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Popup(alignment = Alignment.Center, onDismissRequest = { onDismiss() }) {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp)
                .clip(RoundedCornerShape(10.dp)).background(Color.LightGray)
                .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            Text(text = "Voulez-vous vraiment supprimer cette tâche ?")
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { onDismiss() }, colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black, containerColor = Color.Gray)) {
                            Text(text = "Annuler")
                }

                Button(onClick = { onConfirm() }, colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black, containerColor = Color.Gray)) {
                            Text(text = "Supprimer")
                }
            }
        }
    }
}
