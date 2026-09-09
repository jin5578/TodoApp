package com.example.lock_setup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.utils.LocalSnackbarHostState
import com.example.design_system.utils.LocalSnackbarScope
import com.example.design_system.component.Loading
import com.example.design_system.utils.toErrorMessage
import com.example.lock_setup.model.LockSetupUiEffect
import com.example.lock_setup.model.LockSetupUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.design_system.R as DesignSystemR

@Composable
internal fun LockSetupRoute(
    viewModel: LockSetupViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = LocalSnackbarHostState.current
    val contextResources = LocalResources.current
    val snackbarScope = LocalSnackbarScope.current

    val passwordSetupSuccessMessage =
        stringResource(id = DesignSystemR.string.password_setup_successfully)
    val passwordRemoveSuccessMessage =
        stringResource(id = DesignSystemR.string.password_remove_successfully)

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackbarHostState.showSnackbar(
                message = throwable.toErrorMessage(resources = contextResources)
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEffect.collectLatest { uiEffect ->
            if (uiEffect is LockSetupUiEffect.SuccessSetupPassword) {
                snackbarScope.launch { snackbarHostState.showSnackbar(message = passwordSetupSuccessMessage) }
                popBackStack()
            } else if (uiEffect is LockSetupUiEffect.SuccessRemovePassword) {
                snackbarScope.launch { snackbarHostState.showSnackbar(message = passwordRemoveSuccessMessage) }
                popBackStack()
            }
        }
    }

    LockSetupContent(
        uiState = uiState,
        popBackStack = popBackStack,
        onPasswordCheck = viewModel::checkPassword,
        onNewInputPasswordCheck = viewModel::updateNewInputPassword,
        onPasswordUpdate = viewModel::updatePassword,
        onPasswordRemove = viewModel::removePassword
    )
}

@Composable
private fun LockSetupContent(
    uiState: LockSetupUiState,
    popBackStack: () -> Unit,
    onPasswordCheck: (String) -> Unit,
    onNewInputPasswordCheck: (String) -> Unit,
    onPasswordUpdate: (String) -> Unit,
    onPasswordRemove: () -> Unit
) {
    when (uiState) {
        is LockSetupUiState.Loading ->
            Loading()

        is LockSetupUiState.Screen ->
            LockSetupScreen(
                lockSetupProcessType = uiState.lockSetupProcessType,
                popBackStack = popBackStack,
                onPasswordCheck = onPasswordCheck,
                onNewInputPasswordCheck = onNewInputPasswordCheck,
                onPasswordUpdate = onPasswordUpdate,
                onPasswordRemove = onPasswordRemove
            )
    }
}