package com.example.todoproject.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoproject.APP_TITLE
import com.example.todoproject.components.getInputValue


/**
 * Display the log screen with two input fields for the name and the first name of the user.
 * @param navController the navController to navigate to HomeScreen
 */
@Composable
fun LogScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = APP_TITLE, fontSize = 40.sp)

        Spacer(modifier = Modifier.height(24.dp).background(Color.White))

        Text(text = "Entrer votre nom et votre prenom", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Get the name and the first name of the user for the Header component
        val name = getInputValue("Nom", "Entrez votre nom")

        val firstName = getInputValue("Prenom", "Entrez votre prenom")

        OutlinedButton(onClick = {
            if (name.isNotBlank() && firstName.isNotBlank()) {
                navController.navigate("home/$name/$firstName")
            }
        }) {
            Text(text = "Valider",
                color = Color.Black)
        }
    }
}