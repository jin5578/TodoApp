package com.example.edit_task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.edit_task.model.EditTaskUiEffect
import com.example.edit_task.model.EditTaskUiState
import com.example.model.Task
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun EditTaskRoute(
    viewModel: EditTaskViewModel = hiltViewModel(),
    taskId: Long,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = taskId) {
        viewModel.fetchEditTask(taskId = taskId)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEffect.collectLatest { uiEffect ->
            if (uiEffect is EditTaskUiEffect.SuccessEditTask) {
                val message = uiEffect.message
                onShowMessageSnackbar(message)
                popBackStack()
            }
        }
    }

    EditTaskContent(
        uiState = uiState,
        popBackStack = popBackStack,
        onUpdateTaskClick = viewModel::updateTask,
        onTaskDelete = viewModel::deleteTask,
        onShowMessageSnackbar = onShowMessageSnackbar
    )
}

@Composable
private fun EditTaskContent(
    uiState: EditTaskUiState,
    popBackStack: () -> Unit,
    onUpdateTaskClick: (Task) -> Unit,
    onTaskDelete: (id: Long, uuid: String) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) {
    when (uiState) {
        is EditTaskUiState.Loading ->
            Loading()

        is EditTaskUiState.Screen ->
            EditTaskScreen(
                task = uiState.task,
                locale = uiState.locale,
                timePickerType = uiState.timePickerType,
                categories = uiState.categories,
                popBackStack = popBackStack,
                onUpdateTaskClick = onUpdateTaskClick,
                onTaskDelete = onTaskDelete,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
    }
}