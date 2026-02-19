package com.example.todoproject.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Reusable function to get the value of an input field
 * @param inputTitle the title of the input field
 * @param label the label to display in the input field
 * @return the value of the input field
 */
@Composable
fun getInputValue(inputTitle: String, label: String): String {
    Text(text = inputTitle, style = MaterialTheme.typography.labelLarge)

    var value by remember { mutableStateOf("") }

    // get value
    TextField(value = value, onValueChange = { newText -> value = newText },
        label = { Text(label) }, modifier = Modifier.fillMaxWidth().padding(16.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))
    return value
}