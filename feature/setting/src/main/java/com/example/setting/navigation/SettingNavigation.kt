package com.example.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navigation.Route
import com.example.setting.SettingRoute

fun NavGraphBuilder.settingNavGraph(
    navigateManageCategories: () -> Unit,
    navigateSecurity: () -> Unit,
    navigateGithubAuth: () -> Unit,
    popBackStack: () -> Unit,
) = composable<Route.Setting> {
    SettingRoute(
        navigateManageCategories = navigateManageCategories,
        navigateSecurity = navigateSecurity,
        navigateGithubAuth = navigateGithubAuth,
        popBackStack = popBackStack,
    )
}

fun NavController.navigateSetting() =
    navigate(route = Route.Setting)