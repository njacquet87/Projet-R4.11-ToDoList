package com.example.todoproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoproject.ui.theme.ToDoProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoProjectTheme {
                MaterialTheme {
                    AppNavigation()
                }
            }
        }
    }
}

private const val LOGIN = "login"
private const val APP_TITLE = "// TODO"
private const val HOME = "home"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LOGIN) {

        composable(route = LOGIN) {
            LogScreen(navController)
        }

        composable(route = HOME) {
            HomeScreen(navController)
        }
    }
}

@Composable
fun LogScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = APP_TITLE,
            fontSize = 40.sp)

        Spacer(modifier = Modifier.height(24.dp).background(Color.White))

        Text(text = "Entrer votre nom et votre prenom",
            style = MaterialTheme.typography.bodySmall)

        Text(text = "Nom",
            style = MaterialTheme.typography.labelLarge)

        var name by remember { mutableStateOf("") }

        // get name
        TextField(value = name,
            onValueChange = {newText -> name = newText},
            label = {Text("Entrer votre nom")},
            modifier = Modifier.fillMaxWidth().padding(16.dp))


        Text(text = "Prenom",
            style = MaterialTheme.typography.labelLarge)

        var firstName by remember { mutableStateOf("") }

        // get name
        TextField(value = firstName,
            onValueChange = {newText -> firstName = newText},
            label = {Text("Entrer votre prenom")},
            modifier = Modifier.fillMaxWidth().padding(16.dp))

        OutlinedButton(onClick = {navController.navigate(HOME)}) {
            Text(text = "Valider")
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {

}
