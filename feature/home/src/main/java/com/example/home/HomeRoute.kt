package com.example.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.home.model.HomeUiState
import com.example.model.Task
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    exitApp: () -> Unit,
    navigateSetting: () -> Unit,
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
        passwordCheck = viewModel::checkPassword,
        homeFetch = viewModel::fetchHome,
        taskCompletionUpdate = viewModel::updateTaskCompletion,
        onDeleteAllData = viewModel::deleteAllData,
        onBiometricAuthSucceeded = viewModel::fetchHome,
        onBiometricAuthError = viewModel::executePasswordAuth,
        onAddTask = viewModel::insertTask,
        onTaskSymbolChanged = viewModel::updateTaskSymbol,
        onSubTaskToggleChanged = viewModel::toggleSubTaskCompletion,
        navigateSetting = navigateSetting,
        navigateEditTask = navigateEditTask,
        navigateManageCategories = navigateManageCategories,
        exitApp = exitApp,
        onShowMessageSnackbar = onShowMessageSnackbar,
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    passwordCheck: (String) -> Unit,
    homeFetch: (Long) -> Unit,
    taskCompletionUpdate: (id: Long, isCompleted: Boolean) -> Unit,
    onDeleteAllData: () -> Unit,
    onBiometricAuthSucceeded: () -> Unit,
    onBiometricAuthError: () -> Unit,
    onAddTask: (Task) -> Unit,
    onTaskSymbolChanged: (taskId: Long, symbolId: Int) -> Unit,
    onSubTaskToggleChanged: (subTaskId: Long, isCompleted: Boolean) -> Unit,
    navigateSetting: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    navigateManageCategories: () -> Unit,
    exitApp: () -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
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
                onPasswordCheck = passwordCheck,
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
                onCategoryClick = homeFetch,
                onTaskToggleClick = taskCompletionUpdate,
                onTaskEditClick = navigateEditTask,
                onDeleteSymbolClick = { taskId ->
                    onTaskSymbolChanged(
                        taskId,
                        -1
                    )
                },
                onAddTaskClick = onAddTask,
                onSymbolClick = onTaskSymbolChanged,
                onSubTaskToggleClick = onSubTaskToggleChanged,
                onShowMessageSnackbar = onShowMessageSnackbar
            )
    }
}