package com.example.search_task.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navigation.Route
import com.example.search_task.SearchTaskRoute

fun NavGraphBuilder.searchTaskNavGraph(
    popBackStack: () -> Unit,
) = composable<Route.SearchTask> { _ ->
    SearchTaskRoute(popBackStack = popBackStack)
}

fun NavController.navigateSearchTask() =
    navigate(route = Route.SearchTask)