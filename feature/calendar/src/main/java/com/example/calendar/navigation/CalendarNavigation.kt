package com.example.calendar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.calendar.CalendarRoute
import com.example.navigation.Route
import java.time.LocalDate

fun NavGraphBuilder.calendarNavGraph(
    navigateAddTask: (LocalDate) -> Unit,
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
    onShowErrorSnackBar: (Throwable?) -> Unit,
) = composable<Route.Calendar> {
    CalendarRoute(
        navigateAddTask = navigateAddTask,
        navigateEditTask = navigateEditTask,
        popBackStack = popBackStack,
        onShowErrorSnackbar = onShowErrorSnackBar
    )
}

fun NavController.navigateCalendar() =
    navigate(route = Route.Calendar)