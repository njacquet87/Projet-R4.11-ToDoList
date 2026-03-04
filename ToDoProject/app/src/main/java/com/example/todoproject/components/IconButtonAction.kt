package com.example.todoproject.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Reusable function to display an icon button with an image vector and a description of the icon
 * @param imageVector the icon to display in the button
 * @param contentDescripton the description of the icon
 * @param onClick the action to perform when the button is clicked
 * @param modifier the modifier to apply to the icon. Default value is an empty Modifier
 * @param enabled whether the button is enabled or not. Default value is true
 */
@Composable
fun IconButtonAction(imageVector: ImageVector, contentDescripton: String, onClick: () -> Unit,
                     modifier: Modifier = Modifier, enabled: Boolean = true) {
    IconButton(onClick = onClick, enabled = enabled) {
        Icon(imageVector, contentDescripton, modifier)
    }
}