package com.example.todoproject.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoproject.ViewModel.UserViewModel
import com.example.todoproject.components.buttons.IconButtonAction
import com.example.todoproject.data.entities.UserEntity

/**
 * Display the header of the HomeScreen with the app title
 * Use in every pages
 * @param userViewModel the userViewModel to get the user info
 */
@Composable
fun Header(userViewModel: UserViewModel) {

    var expanded by remember { mutableStateOf(false) }

    Row(Modifier.background(Color.LightGray).fillMaxWidth().height(100.dp).padding(10.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {

        val user = userViewModel.user.collectAsState().value

        Text(text = "//TODO", fontSize = 30.sp)

        if(user != null) {

            Text(text = "Tâches éffectuées : ${user.nbrOfTaskCompleted}", fontSize = 15.sp)

            Box() {
                IconButtonAction(Icons.Default.EmojiEvents, "Trophée",
                    onClick = { expanded = true }, color = user.getBadgeColor())
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.background(Color.LightGray)) {
                    DropdownMenuItem({ Text(text = "20 tâches : Bronze", color = Color.Black) }, onClick = { expanded = false }, trailingIcon = {
                            Icon(Icons.Default.EmojiEvents, contentDescription = "Bronze", tint = UserEntity.bronze)
                        })
                    DropdownMenuItem({ Text(text = "50 tâches : Argent", color = Color.Black) }, onClick = { expanded = false }, trailingIcon = {
                            Icon(Icons.Default.EmojiEvents, contentDescription = "Argent", tint = UserEntity.silver)
                        })
                    DropdownMenuItem({ Text(text = "100 tâches : Or", color = Color.Black) }, onClick = { expanded = false }, trailingIcon = {
                            Icon(Icons.Default.EmojiEvents, contentDescription = "Or", tint = UserEntity.gold)
                        })
                    DropdownMenuItem({ Text(text = "200 tâches : Diamant", color = Color.Black) }, onClick = { expanded = false }, trailingIcon = {
                            Icon(Icons.Default.EmojiEvents, contentDescription = "Diamond", tint = UserEntity.diamond)
                        })
                }
            }
        }
    }
}