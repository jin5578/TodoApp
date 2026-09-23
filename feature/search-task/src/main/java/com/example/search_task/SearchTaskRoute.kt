package com.example.search_task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.utils.toErrorMessage
import com.example.search_task.model.SearchTaskUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SearchTaskRoute(
    viewModel: SearchTaskViewModel = hiltViewModel(),
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

    SearchTaskContent(
        uiState = uiState,
        taskCompletionUpdate = viewModel::updateTaskCompletion,
        onKeywordChanged = viewModel::fetchSearchTaskUiState,
        onTaskSymbolChanged = viewModel::updateTaskSymbol,
        onSubTaskToggleChanged = viewModel::toggleSubTaskCompletion,
        navigateEditTask = navigateEditTask,
        popBackStack = popBackStack,
    )
}

@Composable
private fun SearchTaskContent(
    uiState: SearchTaskUiState,
    taskCompletionUpdate: (id: Long, isCompleted: Boolean) -> Unit,
    onKeywordChanged: (String) -> Unit,
    onTaskSymbolChanged: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleChanged: (subTaskId: Long, isCompleted: Boolean) -> Unit,
    navigateEditTask: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    when (uiState) {
        is SearchTaskUiState.Loading ->
            Loading()

        is SearchTaskUiState.Screen ->
            SearchTaskScreen(
                tasks = uiState.tasks,
                locale = uiState.locale,
                onKeywordChanged = onKeywordChanged,
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
