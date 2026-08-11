package com.example.add_task.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.add_task.AddTaskRoute
import com.example.navigation.Route
import java.time.LocalDate

fun NavGraphBuilder.addTaskNavGraph(
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) = composable<Route.AddTask> { navBackStackEntry ->
    val date = navBackStackEntry.toRoute<Route.AddTask>().date

    AddTaskRoute(
        popBackStack = popBackStack,
        date = LocalDate.parse(date),
        onShowErrorSnackbar = onShowErrorSnackbar,
        onShowMessageSnackbar = onShowMessageSnackbar
    )
}

fun NavController.navigateAddTask(date: LocalDate) =
    navigate(route = Route.AddTask(date = date.toString()))

