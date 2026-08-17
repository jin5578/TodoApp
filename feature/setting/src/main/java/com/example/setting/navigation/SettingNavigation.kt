package com.example.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navigation.Route
import com.example.setting.SettingRoute

fun NavGraphBuilder.settingNavGraph(
    navigateInfo: () -> Unit,
    navigateManageCategories: () -> Unit,
    navigateLockSetup: () -> Unit,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) = composable<Route.Setting> {
    SettingRoute(
        navigateInfo = navigateInfo,
        navigateManageCategories = navigateManageCategories,
        navigateLockSetup = navigateLockSetup,
        popBackStack = popBackStack,
        onShowErrorSnackbar = onShowErrorSnackbar
    )
}

fun NavController.navigateSetting() =
    navigate(route = Route.Setting)