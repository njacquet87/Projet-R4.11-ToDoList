package com.example.todoproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
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

        composable(route = "$HOME/{name}/{firstName}") {
            // get the arguments
            backStackEntry -> val name = backStackEntry.arguments?.getString("name") ?: ""
            val firstName = backStackEntry.arguments?.getString("firstName") ?: ""

            HomeScreen(navController, name, firstName)
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

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        var firstName by remember { mutableStateOf("") }

        // get name
        TextField(value = firstName,
            onValueChange = {newText -> firstName = newText},
            label = {Text("Entrer votre prenom")},
            modifier = Modifier.fillMaxWidth().padding(16.dp))

        OutlinedButton(onClick = {
            if (name.isNotEmpty() && firstName.isNotEmpty()) {
                navController.navigate("$HOME/$name/$firstName")
            }
        }) {
            Text(text = "Valider",
                color = Color.Black)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController, name: String, firstName: String) {

    //header
    Row(Modifier.background(Color.LightGray).fillMaxWidth().height(100.dp).padding(10.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {

        Text(text = APP_TITLE,
            fontSize = 30.sp)

        Text(text = "Bienvenue $name $firstName",
            style = MaterialTheme.typography.bodySmall)
    }

    // body
    Column(Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        // add task
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
            Text(text = "+",
                fontSize = 15.sp,
                modifier = Modifier.border(border = BorderStroke(2.dp, Color.Black),
                    shape = RoundedCornerShape(5.dp)).padding(10.dp, 5.dp))

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = "Ajouter une tache", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // list of tasks
        Column(Modifier.width(250.dp).height(500.dp)
            .border(0.dp, Color.Gray, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(Color.Gray)) {

        }
    }
}
