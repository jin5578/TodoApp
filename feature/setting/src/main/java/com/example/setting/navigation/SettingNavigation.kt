package com.example.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.navigation.Route
import com.example.setting.SettingRoute

fun NavGraphBuilder.settingNavGraph(
    navigateInfo: () -> Unit,
    navigateManageCategories: () -> Unit,
    navigateSecurity: () -> Unit,
    popBackStack: () -> Unit,
) = composable<Route.Setting> {
    SettingRoute(
        navigateInfo = navigateInfo,
        navigateManageCategories = navigateManageCategories,
        navigateSecurity = navigateSecurity,
        popBackStack = popBackStack,
    )
}

fun NavController.navigateSetting(navOptions: NavOptions? = null) =
    navigate(route = Route.Setting, navOptions = navOptions)