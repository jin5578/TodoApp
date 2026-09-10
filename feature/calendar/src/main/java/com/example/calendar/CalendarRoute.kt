package com.example.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calendar.model.CalendarUiState
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.component.Loading
import com.example.design_system.utils.toErrorMessage
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun CalendarRoute(
    viewModel: CalendarViewModel = hiltViewModel(),
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = LocalSnackbarHostState.current
    val contextResources = LocalResources.current

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackbarHostState.showSnackbar(
                message = throwable.toErrorMessage(resources = contextResources)
            )
        }
    }

    CalendarContent(
        uiState = uiState,
        navigateEditTask = navigateEditTask,
        popBackStack = popBackStack,
        onTaskToggleCompletion = viewModel::toggleTaskCompletion,
        onTaskDelete = viewModel::deleteTask
    )
}

@Composable
private fun CalendarContent(
    uiState: CalendarUiState,
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskDelete: (id: Long, uuid: String) -> Unit,
) {
    when (uiState) {
        is CalendarUiState.Loading -> Loading()
        is CalendarUiState.Screen -> CalendarScreen(
            tasks = uiState.tasks,
            categories = uiState.categories,
            locale = uiState.locale,
            navigateEditTask = navigateEditTask,
            popBackStack = popBackStack,
            onTaskToggleCompletion = onTaskToggleCompletion,
            onTaskDelete = onTaskDelete
        )
    }
}