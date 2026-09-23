package com.example.manage_categories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.manage_categories.ManageCategoriesRoute
import com.example.navigation.Route

fun NavGraphBuilder.manageCategoriesNavGraph(
    popBackStack: () -> Unit,
) = composable<Route.ManageCategories> {
    ManageCategoriesRoute(
        popBackStack = popBackStack,
    )
}

fun NavController.navigateManageCategories() = navigate(route = Route.ManageCategories)
