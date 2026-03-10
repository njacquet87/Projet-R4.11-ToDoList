package com.example.todoproject.components.animations

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.todoproject.NotificationHelper
import com.example.todoproject.data.entities.TaskEntity
import kotlinx.coroutines.delay

/**
 * Display a popup with a fireworks animation when the user marks a task as done.
 * The popup is displayed for 5 seconds and then the user is redirected to the HomeScreen.
 * If the user has granted the permission to post notifications, a notification is also displayed.
 * @param onChange called when the animation is finished to update the task list
 * @param navController used to navigate to the HomeScreen after the animation
 * @param context used to show the notification
 * @param task the task that was marked as done, used to display its title in the notification
 */
@Composable
fun MarkAnimation(onChange : () -> Unit, navController: NavController, context: Context, task: TaskEntity?) {
    Popup(alignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp).clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray).border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(10.dp))
            .padding(10.dp)){
            Text(text = "Vous avez réalisé cette tache ! Bravo, une de moins !", style = MaterialTheme.typography.headlineSmall)
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                NotificationHelper.showNotification(context, "Tâche Réalisée !", "Bravo ! Vous venez de réaliser la tâche : ${task?.title ?: "Tâche"}")
            }
        }
    }

    FireworksAnimation(Modifier.fillMaxSize(), 3000, 1f)
    FireworksAnimation(Modifier.fillMaxSize(), 5000, 2f)
    FireworksAnimation(Modifier.fillMaxSize(), 2000, 3f)

    LaunchedEffect(Unit) {
        delay(5000)
        onChange()
        navController.navigate("home")
    }
}