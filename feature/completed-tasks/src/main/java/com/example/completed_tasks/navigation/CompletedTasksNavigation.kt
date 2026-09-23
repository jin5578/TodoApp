package com.example.completed_tasks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.completed_tasks.CompletedTasksRoute
import com.example.navigation.Route

fun NavGraphBuilder.completedTasksNavGraph(
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
) = composable<Route.CompletedTasks> { navBackStackEntry ->
    CompletedTasksRoute(
        navigateEditTask = navigateEditTask,
        popBackStack = popBackStack,
    )
}

fun NavController.navigateCompletedTasks() =
    navigate(route = Route.CompletedTasks)