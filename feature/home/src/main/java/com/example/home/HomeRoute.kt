package com.example.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.design_system.utils.LocalHideBottomBar
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.utils.toErrorMessage
import com.example.home.model.HomeUiState
import com.example.model.CategoryColorType
import com.example.model.SortByType
import com.example.model.Task
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    exitApp: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    navigateCompletedTasks: () -> Unit,
    navigateSearchTask: () -> Unit,
    navigateManageCategories: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = LocalSnackbarHostState.current
    val contextResources = LocalResources.current

    val hideBottomBar = LocalHideBottomBar.current
    SideEffect {
        hideBottomBar.value =
            uiState is HomeUiState.Biometric || uiState is HomeUiState.Password
    }
    DisposableEffect(key1 = Unit) {
        onDispose { hideBottomBar.value = false }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackbarHostState.showSnackbar(
                message = throwable.toErrorMessage(resources = contextResources)
            )
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
        onAddCategory = { categoryTitle, categoryColorType ->
            viewModel.insertCategory(
                title = categoryTitle,
                colorValue = categoryColorType.colorValue
            )
        },
        onSortByTypeChanged = viewModel::updateSortByType,
        navigateEditTask = navigateEditTask,
        navigateCompletedTasks = navigateCompletedTasks,
        navigateSearchTask = navigateSearchTask,
        navigateManageCategories = navigateManageCategories,
        exitApp = exitApp,
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
    onAddCategory: (categoryTitle: String, categoryColorType: CategoryColorType) -> Unit,
    onSortByTypeChanged: (SortByType) -> Unit,
    navigateEditTask: (Long) -> Unit,
    navigateCompletedTasks: () -> Unit,
    navigateSearchTask: () -> Unit,
    navigateManageCategories: () -> Unit,
    exitApp: () -> Unit,
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
                sortByType = uiState.sortByType,
                isVisibleCompletedTask = uiState.isVisibleCompletedTask,
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
                onAddCategoryClick = onAddCategory,
                onSortByTypeChanged = onSortByTypeChanged,
                onCompletedTasksClick = navigateCompletedTasks,
                onSearchClick = navigateSearchTask,
                onManageCategoriesClick = navigateManageCategories,
            )
    }
}