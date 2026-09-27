package com.example.editTask

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designSystem.component.Loading
import com.example.designSystem.utils.LocalSnackbarHostState
import com.example.designSystem.utils.LocalSnackbarScope
import com.example.designSystem.utils.toErrorMessage
import com.example.editTask.model.EditTaskUiEffect
import com.example.editTask.model.EditTaskUiState
import com.example.model.SubTask
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import com.example.designSystem.R as DesignSystemR

@Composable
internal fun EditTaskRoute(
    viewModel: EditTaskViewModel = hiltViewModel(),
    taskId: Long,
    navigateManageCategories: () -> Unit,
    navigateMemo: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = LocalSnackbarHostState.current
    val contextResources = LocalResources.current
    val snackbarScope = LocalSnackbarScope.current

    LaunchedEffect(key1 = taskId) {
        viewModel.fetchEditTaskUiState(taskId = taskId)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackbarHostState.showSnackbar(
                message = throwable.toErrorMessage(resources = contextResources),
            )
        }
    }

    val deleteTaskSuccessMessage =
        stringResource(id = DesignSystemR.string.successfully_deleted_the_schedule)

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEffect.collectLatest { uiEffect ->
            if (uiEffect is EditTaskUiEffect.SuccessDeleteTask) {
                snackbarScope.launch { snackbarHostState.showSnackbar(message = deleteTaskSuccessMessage) }
                popBackStack()
            }
        }
    }

    EditTaskContent(
        uiState = uiState,
        navigateManageCategories = navigateManageCategories,
        navigateMemo = navigateMemo,
        categoryUpdate = viewModel::updateCategory,
        titleUpdate = viewModel::updateTitle,
        dateTimeUpdate = viewModel::updateDateTime,
        completedUpdate = viewModel::updateCompleted,
        taskDelete = viewModel::deleteTask,
        subTasksSync = viewModel::syncSubTasks,
        popBackStack = popBackStack,
    )
}

@Composable
private fun EditTaskContent(
    uiState: EditTaskUiState,
    categoryUpdate: (taskId: Long, categoryId: Long) -> Unit,
    titleUpdate: (taskId: Long, title: String) -> Unit,
    dateTimeUpdate: (taskId: Long, date: LocalDate, time: LocalDateTime?, reminderTime: LocalDateTime?) -> Unit,
    completedUpdate: (taskId: Long, isCompleted: Boolean) -> Unit,
    taskDelete: (id: Long, uuid: String) -> Unit,
    subTasksSync: (parentId: Long, List<SubTask>) -> Unit,
    navigateManageCategories: () -> Unit,
    navigateMemo: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    when (uiState) {
        is EditTaskUiState.Loading ->
            Loading()

        is EditTaskUiState.Screen ->
            EditTaskScreen(
                timePickerType = uiState.timePickerType,
                categories = uiState.categories,
                task = uiState.task,
                locale = uiState.locale,
                onCategoryClick = categoryUpdate,
                onTitleValueChanged = titleUpdate,
                onDateTimeChanged = dateTimeUpdate,
                onCompletedChanged = completedUpdate,
                onDeleteClick = taskDelete,
                onSubTasksSync = subTasksSync,
                navigateManageCategories = navigateManageCategories,
                navigateMemo = navigateMemo,
                popBackStack = popBackStack,
            )
    }
}
