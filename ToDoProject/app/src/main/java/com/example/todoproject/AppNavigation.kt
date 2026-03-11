package com.example.todoproject

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.todoproject.ViewModel.TaskViewModel
import com.example.todoproject.ViewModel.UserViewModel
import com.example.todoproject.pages.AddTaskScreen
import com.example.todoproject.pages.HomeScreen
import com.example.todoproject.pages.DetailScreen
import com.example.todoproject.pages.UpdateScreen

private const val HOME_ROUTE = "home"

private const val DETAIL_ROUTE = "detail/{taskId}"

private const val ADD_ROUTE = "add"

private const val UPDATE_ROUTE = "update/{taskId}"

@Composable
fun AppNavigation(taskViewModel: TaskViewModel, userViewModel: UserViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME_ROUTE) {

        composable(route = HOME_ROUTE) {
            HomeScreen(navController, taskViewModel, userViewModel)
        }

        composable(route = DETAIL_ROUTE,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )) {
            // get taskId to find the corresponding task in the mockTasks list in DetailScreen
            backStackEntry -> val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0

            DetailScreen(navController, taskViewModel, taskId, userViewModel)
        }

        composable(route = ADD_ROUTE) {
            AddTaskScreen(navController, taskViewModel, userViewModel)
        }

        composable(route = UPDATE_ROUTE,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )) {
            // get taskId to find the corresponding task in the mockTasks list in UpdateScreen
            backStackEntry -> val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0

            UpdateScreen(navController, taskViewModel, taskId, userViewModel)
        }
    }
}