package com.example.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.utils.toErrorMessage
import com.example.profile.model.ProfileUiState
import kotlinx.coroutines.flow.collectLatest

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
                message = throwable.toErrorMessage(resources = contextResources)
            )
        }
    }

    ProfileContent(uiState = uiState)
}

@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
) {
    when (uiState) {
        is ProfileUiState.Loading ->
            Loading()

        is ProfileUiState.Screen ->
            ProfileScreen(
                completedTasksCount = uiState.completedTasksCount,
                incompletedTasksCount = uiState.incompletedTasksCount,
                heatmapEntries = uiState.heatmapEntries,
                locale = uiState.locale,
            )
    }
}