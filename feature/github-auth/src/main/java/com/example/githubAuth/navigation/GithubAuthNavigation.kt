package com.example.githubAuth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.githubAuth.GithubAuthRoute
import com.example.navigation.Route

fun NavGraphBuilder.githubAuthNavGraph(
    popBackStack: () -> Unit,
) = composable<Route.GithubAuth> { navBackStackEntry ->
    GithubAuthRoute(
        popBackStack = popBackStack,
    )
}

fun NavController.navigateGithubAuth() = navigate(route = Route.GithubAuth)
