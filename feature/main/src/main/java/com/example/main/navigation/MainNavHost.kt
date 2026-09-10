package com.example.main.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.calendar.navigation.calendarNavGraph
import com.example.completed_tasks.navigation.completedTasksNavGraph
import com.example.edit_task.navigation.editTaskNavGraph
import com.example.home.navigation.homeNavGraph
import com.example.lock_setup.navigation.lockSetupNavGraph
import com.example.manage_categories.navigation.manageCategoriesNavGraph
import com.example.memo.navigation.memoNavGraph
import com.example.search_task.navigation.searchTaskNavGraph
import com.example.security.navigation.securityNavGraph
import com.example.setting.navigation.settingNavGraph
import com.example.tasks.navigation.tasksNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
) {
    Box(
        modifier = modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination
        ) {
            homeNavGraph(
                exitApp = navigator::exitApp,
                navigateEditTask = navigator::navigateEditTask,
                navigateCompletedTasks = navigator::navigateCompletedTasks,
                navigateSearchTask = navigator::navigateSearchTask,
                navigateManageCategories = navigator::navigateManageCategories,
            )
            settingNavGraph(
                navigateInfo = {},
                navigateManageCategories = navigator::navigateManageCategories,
                navigateSecurity = navigator::navigateSecurity,
                popBackStack = navigator::popBackStackIfNotHome,
            )
            manageCategoriesNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
            )
            editTaskNavGraph(
                navigateManageCategories = navigator::navigateManageCategories,
                navigateMemo = navigator::navigateMemo,
                popBackStack = navigator::popBackStackIfNotHome,
            )
            tasksNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
                navigateEditTask = navigator::navigateEditTask,
            )
            calendarNavGraph(
                navigateEditTask = navigator::navigateEditTask,
                popBackStack = navigator::popBackStackIfNotHome,
            )
            lockSetupNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
            )
            securityNavGraph(
                navigateLockSetup = navigator::navigateLockSetup,
                popBackStack = navigator::popBackStackIfNotHome,
            )
            memoNavGraph(
                popBackStack = navigator::popBackStackIfNotHome,
            )
            completedTasksNavGraph(
                navigateEditTask = navigator::navigateEditTask,
                popBackStack = navigator::popBackStackIfNotHome,
            )
            searchTaskNavGraph(
                navigateEditTask = navigator::navigateEditTask,
                popBackStack = navigator::popBackStackIfNotHome
            )
        }
    }
}