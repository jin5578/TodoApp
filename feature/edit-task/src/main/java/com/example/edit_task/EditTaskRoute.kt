package com.example.edit_task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.edit_task.model.EditTaskUiEffect
import com.example.edit_task.model.EditTaskUiState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalDateTime
import com.example.design_system.R as DesignSystemR

@Composable
internal fun EditTaskRoute(
    viewModel: EditTaskViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    taskId: Long,
    navigateManageCategories: () -> Unit,
    navigateMemo: (Long) -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = taskId) {
        viewModel.fetchEditTask(taskId = taskId)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    val deleteTaskSuccessMessage =
        stringResource(id = DesignSystemR.string.successfully_deleted_the_schedule)

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEffect.collectLatest { uiEffect ->
            if (uiEffect is EditTaskUiEffect.SuccessDeleteTask) {
                onShowMessageSnackbar(deleteTaskSuccessMessage)
                popBackStack()
            }
        }
    }

    EditTaskContent(
        popBackStack = popBackStack,
        uiState = uiState,
        navigateManageCategories = navigateManageCategories,
        navigateMemo = navigateMemo,
        categoryUpdate = viewModel::updateCategory,
        titleUpdate = viewModel::updateTitle,
        dateTimeUpdate = viewModel::updateDateTime,
        completedUpdate = viewModel::updateCompleted,
        taskDelete = viewModel::deleteTask,
        onShowMessageSnackbar = onShowMessageSnackbar
    )
}

@Composable
private fun EditTaskContent(
    popBackStack: () -> Unit,
    navigateManageCategories: () -> Unit,
    navigateMemo: (Long) -> Unit,
    uiState: EditTaskUiState,
    categoryUpdate: (taskId: Long, categoryId: Long) -> Unit,
    titleUpdate: (taskId: Long, title: String) -> Unit,
    dateTimeUpdate: (taskId: Long, date: LocalDate, time: LocalDateTime?, reminderTime: LocalDateTime?) -> Unit,
    completedUpdate: (taskId: Long, isCompleted: Boolean) -> Unit,
    taskDelete: (id: Long, uuid: String) -> Unit,
    onShowMessageSnackbar: (String) -> Unit
) {
    when (uiState) {
        is EditTaskUiState.Loading ->
            Loading()

        is EditTaskUiState.Screen ->
            EditTaskScreen(
                popBackStack = popBackStack,
                navigateManageCategories = navigateManageCategories,
                navigateMemo = navigateMemo,
                categories = uiState.categories,
                task = uiState.task,
                locale = uiState.locale,
                onCategoryClick = categoryUpdate,
                onTitleValueChanged = titleUpdate,
                onDateTimeChanged = dateTimeUpdate,
                onCompletedChanged = completedUpdate,
                onDeleteClick = taskDelete,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
    }
}