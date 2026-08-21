package com.example.security

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.security.model.SecurityUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SecurityRoute(
    viewModel: SecurityViewModel = hiltViewModel(),
    navigateLockSetup: () -> Unit,
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    SecurityContent(
        uiState = uiState,
        navigateLockSetup = navigateLockSetup,
        popBackStack = popBackStack,
        onBiometricEnabledChanged = viewModel::updateBiometricEnabled
    )
}

@Composable
private fun SecurityContent(
    uiState: SecurityUiState,
    navigateLockSetup: () -> Unit,
    popBackStack: () -> Unit,
    onBiometricEnabledChanged: (Boolean) -> Unit,
) {
    when (uiState) {
        is SecurityUiState.Loading -> Loading()
        is SecurityUiState.Screen -> SecurityScreen(
            hasExistingPassword = uiState.hasExistingPassword,
            hasBiometricEnabled = uiState.hasBiometricEnabled,
            popBackStack = popBackStack,
            onBiometricEnabledChanged = onBiometricEnabledChanged,
            onPasswordSettingClick = navigateLockSetup,
        )
    }
}