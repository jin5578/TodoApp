package com.example.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.home.model.HomeUiState
import com.example.model.SortTaskType
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    exitApp: () -> Unit,
    navigateCalendar: () -> Unit,
    navigateSetting: () -> Unit,
    navigateAddTask: () -> Unit,
    navigateCompletedTask: (String) -> Unit,
    navigateIncompleteTask: (String) -> Unit,
    navigateThisWeekTask: (String) -> Unit,
    navigateAllTask: (String) -> Unit,
    navigateEditTask: (Long) -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    HomeContent(
        uiState = uiState,
        exitApp = exitApp,
        navigateCalendar = navigateCalendar,
        navigateSetting = navigateSetting,
        navigateAddTask = navigateAddTask,
        navigateCompletedTask = navigateCompletedTask,
        navigateIncompleteTask = navigateIncompleteTask,
        navigateThisWeekTask = navigateThisWeekTask,
        navigateAllTask = navigateAllTask,
        navigateEditTask = navigateEditTask,
        onPasswordCheck = viewModel::checkPassword,
        onSortTaskTypeChanged = viewModel::updateSortTaskType,
        onTaskDelete = viewModel::deleteTask,
        onTaskToggleCompletion = viewModel::toggleTaskCompletion,
        onShowMessageSnackbar = onShowMessageSnackbar,
        onDeleteAllData = viewModel::deleteAllData
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    exitApp: () -> Unit,
    navigateCalendar: () -> Unit,
    navigateSetting: () -> Unit,
    navigateAddTask: () -> Unit,
    navigateCompletedTask: (String) -> Unit,
    navigateIncompleteTask: (String) -> Unit,
    navigateThisWeekTask: (String) -> Unit,
    navigateAllTask: (String) -> Unit,
    navigateEditTask: (Long) -> Unit,
    onPasswordCheck: (String) -> Unit,
    onSortTaskTypeChanged: (SortTaskType) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onTaskToggleCompletion: (id: Long, isCompleted: Boolean) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
    onDeleteAllData: () -> Unit,
) {
    when (uiState) {
        is HomeUiState.Loading ->
            Loading()

        is HomeUiState.Lock ->
            HomeLockScreen(
                lockProcessType = uiState.lockProcessType,
                exitApp = exitApp,
                onPasswordCheck = onPasswordCheck,
                onDeleteAllData = onDeleteAllData
            )

        is HomeUiState.Screen ->
            HomeScreen(
                completedTasks = uiState.completedTasks,
                incompleteTasks = uiState.incompleteTasks,
                categories = uiState.categories,
                sortTaskType = uiState.sortTaskType,
                locale = uiState.locale,
                navigateCalendar = navigateCalendar,
                navigateSetting = navigateSetting,
                navigateAddTask = navigateAddTask,
                navigateCompletedTask = navigateCompletedTask,
                navigateIncompleteTask = navigateIncompleteTask,
                navigateThisWeekTask = navigateThisWeekTask,
                navigateAllTask = navigateAllTask,
                navigateEditTask = navigateEditTask,
                onSortTaskTypeChanged = onSortTaskTypeChanged,
                onTaskDelete = onTaskDelete,
                onTaskToggleCompletion = onTaskToggleCompletion,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
    }
}