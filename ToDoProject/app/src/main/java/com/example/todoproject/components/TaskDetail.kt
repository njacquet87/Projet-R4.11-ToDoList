package com.example.todoproject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoproject.data.TaskEntity

/**
 * Reusable function to display a title and a detail.
 * Ex : title = "Description" and text = "task.description"
 * @param task the task to display
 * @param title the title of the detail
 * @param text the text to display in the detail
 */
@Composable
fun TaskDetail(task: TaskEntity, title: String, text: String) {
    Text(text = title, style = MaterialTheme.typography.labelLarge)
    Text(text = text, fontSize = 15.sp, modifier = Modifier
        .clip(RoundedCornerShape(10.dp)).background(color = Color.LightGray)
        .padding(16.dp, 10.dp, 16.dp, 10.dp))

    Spacer(modifier = Modifier.height(16.dp))
}