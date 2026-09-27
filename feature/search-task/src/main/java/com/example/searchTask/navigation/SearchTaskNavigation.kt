package com.example.searchTask.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navigation.Route
import com.example.searchTask.SearchTaskRoute

fun NavGraphBuilder.searchTaskNavGraph(
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
) = composable<Route.SearchTask> { _ ->
    SearchTaskRoute(
        navigateEditTask = navigateEditTask,
        popBackStack = popBackStack,
    )
}

fun NavController.navigateSearchTask() = navigate(route = Route.SearchTask)
