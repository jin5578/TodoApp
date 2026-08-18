package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.home.HomeRoute
import com.example.navigation.Route

fun NavGraphBuilder.homeNavGraph(
    exitApp: () -> Unit,
    navigateCalendar: () -> Unit,
    navigateSetting: () -> Unit,
    navigateAddTask: () -> Unit,
    navigateCompletedTask: (String) -> Unit,
    navigateIncompleteTask: (String) -> Unit,
    navigateThisWeekTask: (String) -> Unit,
    navigateAllTask: (String) -> Unit,
    navigateEditTask: (Long) -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) = composable<Route.Home> {
    HomeRoute(
        exitApp = exitApp,
        navigateCalendar = navigateCalendar,
        navigateSetting = navigateSetting,
        navigateAddTask = navigateAddTask,
        navigateCompletedTask = navigateCompletedTask,
        navigateIncompleteTask = navigateIncompleteTask,
        navigateThisWeekTask = navigateThisWeekTask,
        navigateAllTask = navigateAllTask,
        navigateEditTask = navigateEditTask,
        onShowErrorSnackbar = onShowErrorSnackbar,
        onShowMessageSnackbar = onShowMessageSnackbar,
    )
}

/*fun NavController.navigateHome(navOptions: NavOptions) =
    navigate(route = Route.Home, navOptions = navOptions)*/

fun NavController.navigateHome() =
    navigate(route = Route.Home)
