package com.example.main.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.calendar.navigation.navigateCalendar
import com.example.completed_tasks.navigation.navigateCompletedTasks
import com.example.edit_task.navigation.navigateEditTask
import com.example.lock_setup.navigation.navigateLockSetup
import com.example.main.model.MainTab
import com.example.manage_categories.navigation.navigateManageCategories
import com.example.memo.navigation.navigateMemo
import com.example.navigation.Route
import com.example.search_task.navigation.navigateSearchTask
import com.example.security.navigation.navigateSecurity
import com.example.setting.navigation.navigateSetting
import com.example.tasks.navigation.navigateTasks

internal class MainNavigator(
    val navController: NavHostController,
    private val activity: Activity,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination = Route.Tasks

    val currentTab: MainTab?
        @Composable get() {
            val destination = currentDestination
            return when {
                destination?.hasRoute<Route.Tasks>() == true -> MainTab.TASKS
                destination?.hasRoute<Route.Calendar>() == true -> MainTab.CALENDAR
                destination?.hasRoute<Route.Setting>() == true -> MainTab.SETTING
                else -> null
            }
        }

    private val topLevelNavOptions: NavOptions
        get() = navOptions {
            popUpTo(id = navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    fun navigateTab(tab: MainTab) =
        when (tab) {
            MainTab.TASKS -> navigateTasks()
            MainTab.CALENDAR -> navigateCalendar()
            MainTab.SETTING -> navigateSetting()
        }

    fun navigateTasks() =
        navController.navigateTasks(navOptions = topLevelNavOptions)

    fun navigateCalendar() =
        navController.navigateCalendar(navOptions = topLevelNavOptions)

    fun navigateSetting() =
        navController.navigateSetting(navOptions = topLevelNavOptions)

    fun navigateEditTask(taskId: Long) =
        navController.navigateEditTask(taskId = taskId)

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

    fun navigateSearchTask() =
        navController.navigateSearchTask()

    private fun popBackStack() =
        navController.popBackStack()

    fun popBackStackIfNotTasks() {
        if (!isSameCurrentDestination<Route.Tasks>()) {
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