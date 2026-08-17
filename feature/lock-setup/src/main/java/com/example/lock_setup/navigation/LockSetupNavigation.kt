package com.example.lock_setup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.lock_setup.LockSetupRoute
import com.example.navigation.Route

fun NavGraphBuilder.lockSetupNavGraph(
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) = composable<Route.LockSetup> {
    LockSetupRoute(
        popBackStack = popBackStack,
        onShowErrorSnackbar = onShowErrorSnackbar,
        onShowMessageSnackbar = onShowMessageSnackbar
    )
}

fun NavController.navigateLockSetup() =
    navigate(route = Route.LockSetup)