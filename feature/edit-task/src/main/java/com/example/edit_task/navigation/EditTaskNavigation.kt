package com.example.edit_task.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.edit_task.EditTaskRoute
import com.example.navigation.Route

fun NavGraphBuilder.editTaskNavGraph(
    popBackStack: () -> Unit,
    navigateManageCategories: () -> Unit,
    navigateMemo: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) = composable<Route.EditTask> { navBackStackEntry ->
    val taskId = navBackStackEntry.toRoute<Route.EditTask>().taskId
    EditTaskRoute(
        taskId = taskId,
        popBackStack = popBackStack,
        navigateManageCategories = navigateManageCategories,
        navigateMemo = navigateMemo,
        onShowErrorSnackbar = onShowErrorSnackbar,
        onShowMessageSnackbar = onShowMessageSnackbar
    )
}

fun NavController.navigateEditTask(taskId: Long) =
    navigate(route = Route.EditTask(taskId = taskId))
