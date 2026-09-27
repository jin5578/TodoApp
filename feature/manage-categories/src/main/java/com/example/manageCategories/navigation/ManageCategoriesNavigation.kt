package com.example.manageCategories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.manageCategories.ManageCategoriesRoute
import com.example.navigation.Route

fun NavGraphBuilder.manageCategoriesNavGraph(
    popBackStack: () -> Unit,
) = composable<Route.ManageCategories> {
    ManageCategoriesRoute(
        popBackStack = popBackStack,
    )
}

fun NavController.navigateManageCategories() = navigate(route = Route.ManageCategories)
