package com.example.security

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.utils.toErrorMessage
import com.example.security.model.SecurityUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SecurityRoute(
    viewModel: SecurityViewModel = hiltViewModel(),
    navigateLockSetup: () -> Unit,
    popBackStack: () -> Unit,
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

    SecurityContent(
        uiState = uiState,
        navigateLockSetup = navigateLockSetup,
        popBackStack = popBackStack,
        onBiometricEnabledChanged = viewModel::updateBiometricEnabled,
    )
}

@Composable
private fun SecurityContent(
    uiState: SecurityUiState,
    onBiometricEnabledChanged: (Boolean) -> Unit,
    navigateLockSetup: () -> Unit,
    popBackStack: () -> Unit,
) {
    when (uiState) {
        is SecurityUiState.Loading -> Loading()

        is SecurityUiState.Screen -> SecurityScreen(
            hasExistingPassword = uiState.hasExistingPassword,
            hasBiometricEnabled = uiState.hasBiometricEnabled,
            onBiometricEnabledChanged = onBiometricEnabledChanged,
            onPasswordSettingClick = navigateLockSetup,
            popBackStack = popBackStack,
        )
    }
}
