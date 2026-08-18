package com.example.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calendar.model.CalendarUiState
import com.example.design_system.component.Loading
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
internal fun CalendarRoute(
    viewModel: CalendarViewModel = hiltViewModel(),
    navigateAddTask: (LocalDate) -> Unit,
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    CalendarContent(
        uiState = uiState,
        navigateAddTask = navigateAddTask,
        navigateEditTask = navigateEditTask,
        popBackStack = popBackStack,
        onTaskToggleCompletion = viewModel::toggleTaskCompletion,
        onTaskDelete = viewModel::deleteTask
    )
}

@Composable
private fun CalendarContent(
    uiState: CalendarUiState,
    navigateAddTask: (LocalDate) -> Unit,
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
) {
    when (uiState) {
        is CalendarUiState.Loading -> Loading()
        is CalendarUiState.Screen -> CalendarScreen(
            tasks = uiState.tasks,
            categories = uiState.categories,
            locale = uiState.locale,
            navigateAddTask = navigateAddTask,
            navigateEditTask = navigateEditTask,
            popBackStack = popBackStack,
            onTaskToggleCompletion = onTaskToggleCompletion,
            onTaskDelete = onTaskDelete
        )
    }
}