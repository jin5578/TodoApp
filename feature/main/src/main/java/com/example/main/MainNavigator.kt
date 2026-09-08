package com.example.main

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.calendar.navigation.navigateCalendar
import com.example.completed_tasks.navigation.navigateCompletedTasks
import com.example.edit_task.navigation.navigateEditTask
import com.example.home.navigation.navigateHome
import com.example.lock_setup.navigation.navigateLockSetup
import com.example.manage_categories.navigation.navigateManageCategories
import com.example.memo.navigation.navigateMemo
import com.example.navigation.Route
import com.example.security.navigation.navigateSecurity
import com.example.setting.navigation.navigateSetting
import com.example.tasks.navigation.navigateTasks

internal class MainNavigator(
    val navController: NavHostController,
    private val activity: Activity,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination = Route.Home

    fun navigateHome() =
        navController.navigateHome()

    fun navigateSetting() =
        navController.navigateSetting()

    fun navigateEditTask(taskId: Long) =
        navController.navigateEditTask(taskId = taskId)

    fun navigateTasks(type: String) =
        navController.navigateTasks(type = type)

    fun navigateCalendar() =
        navController.navigateCalendar()

    fun navigateManageCategories() =
        navController.navigateManageCategories()

    fun navigateMemo(taskId: Long) =
        navController.navigateMemo(taskId = taskId)

    fun navigateLockSetup() =
        navController.navigateLockSetup()

    fun navigateSecurity() =
        navController.navigateSecurity()

    fun navigateCompletedTasks() =
        navController.navigateCompletedTasks()

    private fun popBackStack() =
        navController.popBackStack()

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination<Route.Home>()) {
            popBackStack()
        }
    }

    fun exitApp() =
        activity.finishAffinity()

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean =
        navController.currentDestination?.hasRoute<T>() == true
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator {
    val activity = LocalContext.current as Activity
    return remember(key1 = navController, key2 = activity) {
        MainNavigator(navController = navController, activity = activity)
    }
}