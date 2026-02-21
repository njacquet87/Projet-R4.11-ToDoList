package com.example.todoproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoproject.pages.AddTaskScreen
import com.example.todoproject.pages.HomeScreen
import com.example.todoproject.pages.DetailScreen

private const val HOME = "home"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME) {

        composable(route = "home") {
            HomeScreen(navController)
        }

        composable(route = "detail/{taskId}",
            arguments = listOf(
                navArgument("taskId") { defaultValue = "0" }
            )) {
            // get taskId to find the corresponding task in the mockTasks list in DetailScreen
            backStackEntry -> val taskId = backStackEntry.arguments?.getString("taskId") ?: "0"

            DetailScreen(navController, taskId)
        }

        composable(route = "add") {
            AddTaskScreen(navController)
        }
    }
}