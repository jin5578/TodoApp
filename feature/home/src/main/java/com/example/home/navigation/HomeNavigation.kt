package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.HomeRoute
import com.example.navigation.Route

fun NavGraphBuilder.homeNavGraph(
    exitApp: () -> Unit,
    navigateSetting: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    navigateCompletedTasks: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) = composable<Route.Home> {
    HomeRoute(
        exitApp = exitApp,
        navigateSetting = navigateSetting,
        navigateEditTask = navigateEditTask,
        navigateCompletedTasks = navigateCompletedTasks,
        onShowErrorSnackbar = onShowErrorSnackbar,
        onShowMessageSnackbar = onShowMessageSnackbar,
    )
}

/*fun NavController.navigateHome(navOptions: NavOptions) =
    navigate(route = Route.Home, navOptions = navOptions)*/

fun NavController.navigateHome() =
    navigate(route = Route.Home)
