package com.example.todoproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoproject.pages.HomeScreen
import com.example.todoproject.pages.LogScreen
import com.example.todoproject.pages.DetailScreen

private const val HOME = "home"
private const val LOGIN = "login"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LOGIN) {

        composable(route = LOGIN) {
            LogScreen(navController)
        }

        composable(route = "$HOME/{name}/{firstName}",
            arguments = listOf(
                navArgument("name") { defaultValue = "" },
                navArgument("firstName") { defaultValue = "" }
            )) {
            // get the arguments
                backStackEntry -> val name = backStackEntry.arguments?.getString("name") ?: ""
            val firstName = backStackEntry.arguments?.getString("firstName") ?: ""

            HomeScreen(navController, name, firstName)
        }

        composable(route = "detail/{name}/{firstName}/{taskId}",
            arguments = listOf(
                navArgument("name") { defaultValue = "" },
                navArgument("firstName") { defaultValue = "" },
                navArgument("taskId") { defaultValue = "0" }
            )) {
                backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val firstName = backStackEntry.arguments?.getString("firstName") ?: ""

            // get taskId to find the corresponding task in the mockTasks list in DetailScreen
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull() ?: -1

            DetailScreen(navController, name, firstName, taskId.toString())
        }
    }
}