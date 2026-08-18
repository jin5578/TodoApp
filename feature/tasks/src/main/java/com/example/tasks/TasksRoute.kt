package com.example.tasks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.model.TasksType
import com.example.tasks.model.TasksUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun TasksRoute(
    viewModel: TasksViewModel = hiltViewModel(),
    type: TasksType,
    popBackStack: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = type) {
        viewModel.fetchTasks(type = type)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    TasksContent(
        uiState = uiState,
        type = type,
        popBackStack = popBackStack,
        navigateEditTask = navigateEditTask,
        onTaskToggleCompletion = { taskId, isCompleted ->
            viewModel.toggleTaskCompletion(
                taskId = taskId,
                isCompleted = isCompleted
            )
        },
        onTaskDelete = viewModel::deleteTask,
        onShowMessageSnackbar = onShowMessageSnackbar
    )
}

@Composable
private fun TasksContent(
    uiState: TasksUiState,
    type: TasksType,
    popBackStack: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) {
    when (uiState) {
        is TasksUiState.Loading ->
            Loading()

        is TasksUiState.Screen ->
            TasksScreen(
                type = type,
                tasks = uiState.tasks,
                categories = uiState.categories,
                locale = uiState.locale,
                popBackStack = popBackStack,
                navigateEditTask = navigateEditTask,
                onTaskToggleCompletion = onTaskToggleCompletion,
                onTaskDelete = onTaskDelete,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
    }
}