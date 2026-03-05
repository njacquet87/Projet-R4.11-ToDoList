package com.example.todoproject.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Reusable function to display a TextField with a title and a label.
 * The value is used to get the value of the TextField
 * and onValueChange is used to update the value of the TextField when it changes.
 * @param value the value of the TextField
 * @param onValueChange the action to perform when the value of the TextField changes
 * @param inputTitle the title to display above the TextField
 * @param label the label to display inside the TextField
 */
@Composable
fun AppTextField(value: String?, onValueChange: (String) -> Unit, inputTitle: String, label: String) {
    Text(text = inputTitle, style = MaterialTheme.typography.labelLarge)

    if (value != null) {
        TextField(value = value, onValueChange = onValueChange, label = { Text(label, fontSize = 12.sp) },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.LightGray, unfocusedContainerColor = Color.LightGray,
                focusedLabelColor = Color.Black, unfocusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.Black, unfocusedIndicatorColor = Color.Black,
                cursorColor = Color.Black, focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}