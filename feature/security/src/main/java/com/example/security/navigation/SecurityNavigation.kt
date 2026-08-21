package com.example.security.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navigation.Route
import com.example.security.SecurityRoute

fun NavGraphBuilder.securityNavGraph(
    navigateLockSetup: () -> Unit,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) = composable<Route.Security> {
    SecurityRoute(
        navigateLockSetup = navigateLockSetup,
        popBackStack = popBackStack,
        onShowErrorSnackbar = onShowErrorSnackbar,
        onShowMessageSnackbar = onShowMessageSnackbar
    )
}

fun NavController.navigateSecurity() =
    navigate(route = Route.Security)