package com.example.editTask.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.editTask.EditTaskRoute
import com.example.navigation.Route

fun NavGraphBuilder.editTaskNavGraph(
    navigateManageCategories: () -> Unit,
    navigateMemo: (Long) -> Unit,
    popBackStack: () -> Unit,
) = composable<Route.EditTask> { navBackStackEntry ->
    val taskId = navBackStackEntry.toRoute<Route.EditTask>().taskId
    EditTaskRoute(
        taskId = taskId,
        navigateManageCategories = navigateManageCategories,
        navigateMemo = navigateMemo,
        popBackStack = popBackStack,
    )
}

fun NavController.navigateEditTask(taskId: Long) = navigate(route = Route.EditTask(taskId = taskId))
