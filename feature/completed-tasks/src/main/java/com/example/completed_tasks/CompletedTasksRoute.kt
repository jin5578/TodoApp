package com.example.completed_tasks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.completed_tasks.model.CompletedTasksUiState
import com.example.design_system.component.Loading
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.utils.toErrorMessage
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun CompletedTasksRoute(
    viewModel: CompletedTasksViewModel = hiltViewModel(),
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = LocalSnackbarHostState.current
    val contextResources = LocalResources.current

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackbarHostState.showSnackbar(
                message = throwable.toErrorMessage(resources = contextResources),
            )
        }
    }

    CompletedTasksContent(
        uiState = uiState,
        taskCompletionUpdate = viewModel::updateTaskCompletion,
        onDeleteClick = viewModel::deleteAllCompletedTasks,
        onTaskSymbolChanged = viewModel::updateTaskSymbol,
        onSubTaskToggleChanged = viewModel::toggleSubTaskCompletion,
        navigateEditTask = navigateEditTask,
        popBackStack = popBackStack,
    )
}

@Composable
private fun CompletedTasksContent(
    uiState: CompletedTasksUiState,
    taskCompletionUpdate: (id: Long, isCompleted: Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onTaskSymbolChanged: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleChanged: (subTaskId: Long, isCompleted: Boolean) -> Unit,
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    when (uiState) {
        is CompletedTasksUiState.Loading -> Loading()

        is CompletedTasksUiState.Screen -> CompletedTasksScreen(
            taskDateGroups = uiState.taskDateGroups,
            locale = uiState.locale,
            onDeleteClick = onDeleteClick,
            onTaskToggleClick = taskCompletionUpdate,
            onTaskEditClick = navigateEditTask,
            onDeleteSymbolClick = { taskId ->
                onTaskSymbolChanged(
                    taskId,
                    -1,
                )
            },
            onSymbolClick = onTaskSymbolChanged,
            onSubTaskToggleClick = onSubTaskToggleChanged,
            popBackStack = popBackStack,
        )
    }
}
