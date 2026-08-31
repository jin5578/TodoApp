package com.example.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.home.model.HomeUiState
import com.example.model.SortTaskType
import com.example.model.Task
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    exitApp: () -> Unit,
    navigateCalendar: () -> Unit,
    navigateSetting: () -> Unit,
    navigateCompletedTask: (String) -> Unit,
    navigateIncompleteTask: (String) -> Unit,
    navigateThisWeekTask: (String) -> Unit,
    navigateAllTask: (String) -> Unit,
    navigateEditTask: (Long) -> Unit,
    navigateManageCategories: () -> Unit,
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
        navigateCompletedTask = navigateCompletedTask,
        navigateIncompleteTask = navigateIncompleteTask,
        navigateThisWeekTask = navigateThisWeekTask,
        navigateAllTask = navigateAllTask,
        navigateEditTask = navigateEditTask,
        navigateManageCategories = navigateManageCategories,
        onPasswordCheck = viewModel::checkPassword,
        onCategoryChanged = viewModel::fetchHome,
        onSortTaskTypeChanged = viewModel::updateSortTaskType,
        onTaskDelete = viewModel::deleteTask,
        onTaskToggleChanged = viewModel::toggleTaskCompletion,
        onShowMessageSnackbar = onShowMessageSnackbar,
        onDeleteAllData = viewModel::deleteAllData,
        onBiometricAuthSucceeded = viewModel::fetchHome,
        onBiometricAuthError = viewModel::executePasswordAuth,
        onAddTask = viewModel::insertTask,
        onTaskSymbolChanged = viewModel::updateTaskSymbol
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    exitApp: () -> Unit,
    navigateCalendar: () -> Unit,
    navigateSetting: () -> Unit,
    navigateCompletedTask: (String) -> Unit,
    navigateIncompleteTask: (String) -> Unit,
    navigateThisWeekTask: (String) -> Unit,
    navigateAllTask: (String) -> Unit,
    navigateEditTask: (Long) -> Unit,
    navigateManageCategories: () -> Unit,
    onPasswordCheck: (String) -> Unit,
    onCategoryChanged: (Long) -> Unit,
    onSortTaskTypeChanged: (SortTaskType) -> Unit,
    onTaskDelete: (Long) -> Unit,
    onTaskToggleChanged: (id: Long, isCompleted: Boolean) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
    onDeleteAllData: () -> Unit,
    onBiometricAuthSucceeded: () -> Unit,
    onBiometricAuthError: () -> Unit,
    onAddTask: (Task) -> Unit,
    onTaskSymbolChanged: (taskId: Long, symbolId: Int) -> Unit,
) {
    when (uiState) {
        is HomeUiState.Loading ->
            Loading()

        is HomeUiState.Biometric ->
            HomeBiometricScreen(
                onBiometricAuthSucceeded = onBiometricAuthSucceeded,
                onBiometricAuthError = onBiometricAuthError
            )

        is HomeUiState.Password ->
            HomePasswordScreen(
                homePasswordProcessType = uiState.homePasswordProcessType,
                exitApp = exitApp,
                onPasswordCheck = onPasswordCheck,
                onDeleteAllData = onDeleteAllData
            )

        is HomeUiState.Screen ->
            HomeScreen(
                categories = uiState.categories,
                taskStateGroups = uiState.taskStateGroups,
                locale = uiState.locale,
                timePickerType = uiState.timePickerType,
                navigateManageCategories = navigateManageCategories,
                onSettingClick = navigateSetting,
                onCategoryClick = onCategoryChanged,
                onTaskToggleClick = onTaskToggleChanged,
                onTaskEditClick = navigateEditTask,
                onDeleteSymbolClick = { taskId ->
                    onTaskSymbolChanged(
                        taskId,
                        -1
                    )
                },
                onSymbolClick = onTaskSymbolChanged,
                onAddTaskClick = onAddTask,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
    }
}