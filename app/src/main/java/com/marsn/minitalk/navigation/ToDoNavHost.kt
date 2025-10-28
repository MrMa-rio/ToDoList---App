package com.marsn.minitalk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.marsn.minitalk.ui.feature.addedit.AddEditToDoScreen
import com.marsn.minitalk.ui.feature.list.ListScreen

@Composable
fun ToDoNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route
    ) {

        composable<Route> {
            ListScreen(
                navigateToAddEditScreen = { id ->

                    navController.navigate(AddEditRoute(id = id))

                }
            )
        }

        composable<AddEditRoute> { backStackEntry ->
            val todoId = backStackEntry.toRoute<AddEditRoute>()

            AddEditToDoScreen(
                id = todoId.id,
                navigateBack = {
                    navController.popBackStack()
                })
        }

    }
}