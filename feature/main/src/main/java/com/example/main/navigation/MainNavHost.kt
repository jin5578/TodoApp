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
import com.example.github_auth.navigation.githubAuthNavGraph
import com.example.lock_setup.navigation.lockSetupNavGraph
import com.example.manage_categories.navigation.manageCategoriesNavGraph
import com.example.memo.navigation.memoNavGraph
import com.example.profile.navigation.profileNavGraph
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
            tasksNavGraph(
                exitApp = navigator::exitApp,
                navigateEditTask = navigator::navigateEditTask,
                navigateCompletedTasks = navigator::navigateCompletedTasks,
                navigateSearchTask = navigator::navigateSearchTask,
                navigateManageCategories = navigator::navigateManageCategories,
                navigateSetting = navigator::navigateSetting
            )
            calendarNavGraph(
                navigateEditTask = navigator::navigateEditTask,
            )
            profileNavGraph()
            settingNavGraph(
                navigateManageCategories = navigator::navigateManageCategories,
                navigateSecurity = navigator::navigateSecurity,
                navigateGithubAuth = navigator::navigateGithubAuth,
                popBackStack = navigator::popBackStackIfNotTasks,
            )
            manageCategoriesNavGraph(
                popBackStack = navigator::popBackStackIfNotTasks,
            )
            editTaskNavGraph(
                navigateManageCategories = navigator::navigateManageCategories,
                navigateMemo = navigator::navigateMemo,
                popBackStack = navigator::popBackStackIfNotTasks,
            )
            lockSetupNavGraph(
                popBackStack = navigator::popBackStackIfNotTasks,
            )
            securityNavGraph(
                navigateLockSetup = navigator::navigateLockSetup,
                popBackStack = navigator::popBackStackIfNotTasks,
            )
            memoNavGraph(
                popBackStack = navigator::popBackStackIfNotTasks,
            )
            completedTasksNavGraph(
                navigateEditTask = navigator::navigateEditTask,
                popBackStack = navigator::popBackStackIfNotTasks,
            )
            searchTaskNavGraph(
                navigateEditTask = navigator::navigateEditTask,
                popBackStack = navigator::popBackStackIfNotTasks
            )
            githubAuthNavGraph(
                popBackStack = navigator::popBackStackIfNotTasks
            )
        }
    }
}