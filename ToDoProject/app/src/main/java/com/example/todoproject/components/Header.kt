package com.example.todoproject.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoproject.APP_TITLE

/**
 * Display the header of the HomeScreen with the app title and a welcome message
 * Use in every pages
 * @param name the name of the user
 * @param firstName the first name of the user
 */
@Composable
fun Header(name: String, firstName: String) {
    Row(Modifier.background(Color.LightGray).fillMaxWidth().height(100.dp).padding(10.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

        Text(text = APP_TITLE, fontSize = 30.sp)

        Text(text = "Bienvenue $name $firstName", style = MaterialTheme.typography.bodySmall)
    }
}