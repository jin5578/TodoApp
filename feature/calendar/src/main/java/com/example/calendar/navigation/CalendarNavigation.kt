package com.example.calendar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.calendar.CalendarRoute
import com.example.navigation.Route

fun NavGraphBuilder.calendarNavGraph(
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
) = composable<Route.Calendar> {
    CalendarRoute(
        navigateEditTask = navigateEditTask,
        popBackStack = popBackStack,
    )
}

fun NavController.navigateCalendar(navOptions: NavOptions? = null) =
    navigate(route = Route.Calendar, navOptions = navOptions)