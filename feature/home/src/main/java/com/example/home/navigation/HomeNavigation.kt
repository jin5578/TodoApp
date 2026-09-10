package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.home.HomeRoute
import com.example.navigation.Route

fun NavGraphBuilder.homeNavGraph(
    exitApp: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    navigateCompletedTasks: () -> Unit,
) = composable<Route.Home> {
    HomeRoute(
        exitApp = exitApp,
        navigateEditTask = navigateEditTask,
        navigateCompletedTasks = navigateCompletedTasks,
    )
}

fun NavController.navigateHome(navOptions: NavOptions? = null) =
    navigate(route = Route.Home, navOptions = navOptions)
