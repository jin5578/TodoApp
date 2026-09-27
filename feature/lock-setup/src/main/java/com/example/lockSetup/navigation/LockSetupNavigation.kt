package com.example.lockSetup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.lockSetup.LockSetupRoute
import com.example.navigation.Route

fun NavGraphBuilder.lockSetupNavGraph(
    popBackStack: () -> Unit,
) = composable<Route.LockSetup> {
    LockSetupRoute(
        popBackStack = popBackStack,
    )
}

fun NavController.navigateLockSetup() = navigate(route = Route.LockSetup)
