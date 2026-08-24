package com.example.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.add_task.navigation.addTaskNavGraph
import com.example.calendar.navigation.calendarNavGraph
import com.example.edit_task.navigation.editTaskNavGraph
import com.example.home.navigation.homeNavGraph
import com.example.lock_setup.navigation.lockSetupNavGraph
import com.example.manage_categories.navigation.manageCategoriesNavGraph
import com.example.security.navigation.securityNavGraph
import com.example.setting.navigation.settingNavGraph
import com.example.tasks.navigation.tasksNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination
        ) {
            homeNavGraph(
                exitApp = navigator::exitApp,
                navigateCalendar = navigator::navigateCalendar,
                navigateSetting = navigator::navigateSetting,
                navigateAddTask = navigator::navigateAddTask,
                navigateCompletedTask = navigator::navigateTasks,
                navigateIncompleteTask = navigator::navigateTasks,
                navigateThisWeekTask = navigator::navigateTasks,
                navigateAllTask = navigator::navigateTasks,
                navigateEditTask = navigator::navigateEditTask,
                navigateManageCategories = navigator::navigateManageCategories,
                onShowErrorSnackbar = onShowErrorSnackbar,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
            addTaskNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackbar = onShowErrorSnackbar,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
            settingNavGraph(
                navigateInfo = {},
                navigateManageCategories = navigator::navigateManageCategories,
                navigateSecurity = navigator::navigateSecurity,
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackbar = onShowErrorSnackbar,
            )
            manageCategoriesNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackbar = onShowErrorSnackbar,
            )
            editTaskNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackbar = onShowErrorSnackbar,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
            tasksNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                navigateEditTask = navigator::navigateEditTask,
                onShowErrorSnackBar = onShowErrorSnackbar,
                onShowMessageSnackBar = onShowMessageSnackbar
            )
            calendarNavGraph(
                navigateAddTask = navigator::navigateAddTask,
                navigateEditTask = navigator::navigateEditTask,
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackBar = onShowErrorSnackbar
            )
            lockSetupNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackbar = onShowErrorSnackbar,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
            securityNavGraph(
                navigateLockSetup = navigator::navigateLockSetup,
                popBackStack = navigator::popBackStackIfNotHome,
                onShowErrorSnackbar = onShowErrorSnackbar,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
        }
    }
}