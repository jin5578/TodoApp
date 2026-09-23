package com.example.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calendar.model.CalendarUiState
import com.example.design_system.component.Loading
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.utils.toErrorMessage
import com.example.model.CategoryColorType
import com.example.model.Task
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
internal fun CalendarRoute(
    viewModel: CalendarViewModel = hiltViewModel(),
    navigateEditTask: (Long) -> Unit,
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

    CalendarContent(
        uiState = uiState,
        onCategorySelected = viewModel::onCategorySelected,
        onDateSelected = viewModel::onDateSelected,
        onAddTask = viewModel::insertTask,
        onAddCategory = { categoryTitle, categoryColorType ->
            viewModel.insertCategory(
                title = categoryTitle,
                colorValue = categoryColorType.colorValue,
            )
        },
        onTaskToggleCompletion = viewModel::updateTaskCompleted,
        onTaskSymbolChanged = viewModel::updateTaskSymbol,
        onSubTaskToggleChanged = viewModel::updateSubTaskCompleted,
        navigateEditTask = navigateEditTask,
    )
}

@Composable
private fun CalendarContent(
    uiState: CalendarUiState,
    onCategorySelected: (Long) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onAddTask: (Task) -> Unit,
    onAddCategory: (categoryTitle: String, categoryColorType: CategoryColorType) -> Unit,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onTaskSymbolChanged: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleChanged: (subTaskId: Long, isCompleted: Boolean) -> Unit,
    navigateEditTask: (Long) -> Unit,
) {
    when (uiState) {
        is CalendarUiState.Loading -> Loading()

        is CalendarUiState.Screen -> CalendarScreen(
            calendarTasks = uiState.calendarTasks,
            tasks = uiState.tasks,
            categories = uiState.categories,
            locale = uiState.locale,
            timePickerType = uiState.timePickerType,
            selectedDate = uiState.selectedDate,
            selectedCategoryId = uiState.selectedCategoryId,
            onCategorySelected = onCategorySelected,
            onDateSelected = onDateSelected,
            onAddTaskClick = onAddTask,
            onAddCategoryClick = onAddCategory,
            onTaskToggleCompletion = onTaskToggleCompletion,
            onDeleteSymbolClick = { taskId ->
                onTaskSymbolChanged(
                    taskId,
                    -1,
                )
            },
            onSymbolClick = onTaskSymbolChanged,
            onSubTaskToggleClick = onSubTaskToggleChanged,
            navigateEditTask = navigateEditTask,
        )
    }
}
