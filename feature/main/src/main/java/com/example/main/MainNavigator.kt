package com.example.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.add_task.navigation.navigateAddTask
import com.example.manage_categories.navigation.navigateManageCategories
import com.example.navigation.Route
import java.time.LocalDate

internal class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination = Route.Setting

    fun navigateAddTask(date: LocalDate = LocalDate.now()) =
        navController.navigateAddTask(date = date)

    fun navigateManageCategories() =
        navController.navigateManageCategories()

    private fun popBackStack() =
        navController.popBackStack()

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination<Route.Home>()) {
            popBackStack()
        }
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean =
        navController.currentDestination?.hasRoute<T>() == true
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(key1 = navController) {
    MainNavigator(navController = navController)
}