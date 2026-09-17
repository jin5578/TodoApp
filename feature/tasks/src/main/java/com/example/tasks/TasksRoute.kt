package com.example.tasks

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.design_system.utils.LocalHideBottomBar
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.utils.toErrorMessage
import com.example.model.CategoryColorType
import com.example.model.SortByType
import com.example.model.Task
import com.example.tasks.model.TasksUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun TasksRoute(
    viewModel: TasksViewModel = hiltViewModel(),
    exitApp: () -> Unit,
    navigateEditTask: (Long) -> Unit,
    navigateCompletedTasks: () -> Unit,
    navigateSearchTask: () -> Unit,
    navigateManageCategories: () -> Unit,
    navigateSetting: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val contextResources = LocalResources.current

    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) viewModel.fetchWeather()
    }

    LaunchedEffect(key1 = Unit) {
        val hasLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasLocationPermission) {
            viewModel.fetchWeather()
        } else {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    val hideBottomBar = LocalHideBottomBar.current
    SideEffect {
        hideBottomBar.value =
            uiState is TasksUiState.Biometric || uiState is TasksUiState.Password
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

    TasksContent(
        uiState = uiState,
        passwordCheck = viewModel::checkPassword,
        tasksFetch = viewModel::fetchTasksUiState,
        taskCompletionUpdate = viewModel::updateTaskCompleted,
        onDeleteAllData = viewModel::deleteAllData,
        onBiometricAuthSucceeded = viewModel::fetchTasksUiState,
        onBiometricAuthError = viewModel::executePasswordAuth,
        onAddTask = viewModel::insertTask,
        onTaskSymbolChanged = viewModel::updateTaskSymbol,
        onSubTaskToggleChanged = viewModel::updateSubTaskCompleted,
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
        navigateSetting = navigateSetting,
        exitApp = exitApp,
    )
}

@Composable
private fun TasksContent(
    uiState: TasksUiState,
    passwordCheck: (String) -> Unit,
    tasksFetch: (Long) -> Unit,
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
    navigateSetting: () -> Unit,
    exitApp: () -> Unit,
) {
    when (uiState) {
        is TasksUiState.Loading ->
            Loading()

        is TasksUiState.Biometric ->
            TasksBiometricScreen(
                onBiometricAuthSucceeded = onBiometricAuthSucceeded,
                onBiometricAuthError = onBiometricAuthError
            )

        is TasksUiState.Password ->
            TasksPasswordScreen(
                tasksPasswordProcessType = uiState.tasksPasswordProcessType,
                exitApp = exitApp,
                onPasswordCheck = passwordCheck,
                onDeleteAllData = onDeleteAllData
            )

        is TasksUiState.Screen ->
            TasksScreen(
                categories = uiState.categories,
                taskStateGroups = uiState.taskStateGroups,
                locale = uiState.locale,
                timePickerType = uiState.timePickerType,
                sortByType = uiState.sortByType,
                isVisibleCompletedTask = uiState.isVisibleCompletedTask,
                onCategoryClick = tasksFetch,
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
                onSettingClick = navigateSetting,
            )
    }
}