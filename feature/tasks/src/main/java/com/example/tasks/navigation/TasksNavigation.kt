package com.example.tasks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.model.toTasksType
import com.example.navigation.Route
import com.example.tasks.TasksRoute

fun NavGraphBuilder.tasksNavGraph(
    popBackStack: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    onShowErrorSnackBar: (Throwable?) -> Unit,
    onShowMessageSnackBar: (String) -> Unit,
) = composable<Route.Tasks> { navBackStackEntry ->
    val type = navBackStackEntry.toRoute<Route.Tasks>().type.toTasksType()
    TasksRoute(
        type = type,
        popBackStack = popBackStack,
        navigateEditTask = navigateEditTask,
        onShowErrorSnackbar = onShowErrorSnackBar,
        onShowMessageSnackbar = onShowMessageSnackBar
    )
}

fun NavController.navigateTasks(type: String) =
    navigate(route = Route.Tasks(type = type))
