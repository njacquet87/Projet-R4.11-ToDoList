package com.example.todoproject.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoproject.APP_TITLE
import com.example.todoproject.components.AppTextField


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
        var name by remember { mutableStateOf("") }
        AppTextField(value = name, onValueChange = { newText -> name = newText }, inputTitle = "Nom", label = "Entrez votre nom")

        var firstName by remember { mutableStateOf("") }
        AppTextField(value = firstName, onValueChange = { newText -> firstName = newText }, inputTitle = "Prenom", label = "Entrez votre prenom")

        Button(onClick = {
            if (name.isNotBlank() && firstName.isNotBlank()) {
                navController.navigate("home/$name/$firstName")
            }
        }, colors = ButtonDefaults.buttonColors(contentColor = Color.Black, containerColor = Color.LightGray)) {
            Text(text = "Valider", color = Color.Black)
        }
    }
}