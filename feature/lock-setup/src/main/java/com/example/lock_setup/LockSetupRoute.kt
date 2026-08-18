package com.example.lock_setup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.design_system.component.Loading
import com.example.lock_setup.model.LockSetupUiEffect
import com.example.lock_setup.model.LockSetupUiState
import kotlinx.coroutines.flow.collectLatest
import com.example.design_system.R as DesignSystemR

@Composable
internal fun LockSetupRoute(
    viewModel: LockSetupViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    onShowMessageSnackbar: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val passwordSetupSuccessMessage =
        stringResource(id = DesignSystemR.string.password_setup_successfully)
    val passwordRemoveSuccessMessage =
        stringResource(id = DesignSystemR.string.password_remove_successfully)

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collectLatest { throwable ->
            onShowErrorSnackbar(throwable)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEffect.collectLatest { uiEffect ->
            if (uiEffect is LockSetupUiEffect.SuccessSetupPassword) {
                onShowMessageSnackbar(passwordSetupSuccessMessage)
                popBackStack()
            } else if (uiEffect is LockSetupUiEffect.SuccessRemovePassword) {
                onShowMessageSnackbar(passwordRemoveSuccessMessage)
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