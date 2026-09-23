package com.example.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.navigation.Route
import com.example.profile.ProfileRoute

fun NavGraphBuilder.profileNavGraph() = composable<Route.Profile> { navBackStackEntry ->
    ProfileRoute()
}

fun NavController.navigateProfile(navOptions: NavOptions? = null) = navigate(route = Route.Profile, navOptions = navOptions)
