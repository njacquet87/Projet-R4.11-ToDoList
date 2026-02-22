package com.example.todoproject

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.data.AppContainer
import com.example.todoproject.pages.AddTaskScreen
import com.example.todoproject.pages.HomeScreen
import com.example.todoproject.pages.DetailScreen

private const val HOME = "home"

@Composable
fun AppNavigation(viewModel: TaskViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME) {

        composable(route = "home") {
            HomeScreen(navController, viewModel)
        }

        composable(route = "detail/{taskId}",
            arguments = listOf(
                navArgument("taskId") { defaultValue = "0" }
            )) {
            // get taskId to find the corresponding task in the mockTasks list in DetailScreen
            backStackEntry -> val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0

            DetailScreen(navController, viewModel, taskId)
        }

        composable(route = "add") {
            AddTaskScreen(navController, viewModel)
        }
    }
}