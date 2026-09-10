package com.example.tasks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.navigation.Route
import com.example.tasks.TasksRoute

fun NavGraphBuilder.tasksNavGraph(
    exitApp: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    navigateCompletedTasks: () -> Unit,
    navigateSearchTask: () -> Unit,
    navigateManageCategories: () -> Unit,
) = composable<Route.Tasks> {
    TasksRoute(
        exitApp = exitApp,
        navigateEditTask = navigateEditTask,
        navigateCompletedTasks = navigateCompletedTasks,
        navigateSearchTask = navigateSearchTask,
        navigateManageCategories = navigateManageCategories,
    )
}

fun NavController.navigateTasks(navOptions: NavOptions? = null) =
    navigate(route = Route.Tasks, navOptions = navOptions)