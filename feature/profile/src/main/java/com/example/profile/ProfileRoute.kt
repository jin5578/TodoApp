package com.example.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designSystem.component.Loading
import com.example.designSystem.utils.LocalSnackbarHostState
import com.example.designSystem.utils.toErrorMessage
import com.example.profile.model.ProfileTaskDuration
import com.example.profile.model.ProfileTaskState
import com.example.profile.model.ProfileUiState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
internal fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
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

    ProfileContent(
        uiState = uiState,
        onTaskStateChanged = viewModel::onTaskStateChanged,
        onTaskDurationChanged = viewModel::onTaskDurationChanged,
        onDailyDateRangeChanged = viewModel::onDailyDateRangeChanged,
    )
}

@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    onTaskStateChanged: (ProfileTaskState) -> Unit,
    onTaskDurationChanged: (ProfileTaskDuration) -> Unit,
    onDailyDateRangeChanged: (fromDate: LocalDate, toDate: LocalDate) -> Unit,
) {
    when (uiState) {
        is ProfileUiState.Loading ->
            Loading()

        is ProfileUiState.Screen ->
            ProfileScreen(
                completedTasksCount = uiState.completedTasksCount,
                incompletedTasksCount = uiState.incompletedTasksCount,
                heatmapEntries = uiState.heatmapEntries,
                githubHeatmapEntries = uiState.githubHeatmapEntries,
                categoryEntries = uiState.categoryEntries,
                categoryTaskState = uiState.categoryTaskState,
                categoryTaskDuration = uiState.categoryTaskDuration,
                dailyEntries = uiState.dailyEntries,
                dailyFromDate = uiState.dailyFromDate,
                dailyToDate = uiState.dailyToDate,
                locale = uiState.locale,
                onTaskStateChanged = onTaskStateChanged,
                onTaskDurationChanged = onTaskDurationChanged,
                onDailyDateRangeChanged = onDailyDateRangeChanged,
            )
    }
}
