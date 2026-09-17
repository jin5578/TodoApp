package com.example.github_auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.github_auth.model.GithubAuthUiState
import com.example.utils.openUrl
import kotlinx.coroutines.delay

private const val SUCCESS_AUTO_POP_DELAY_MS = 1200L

@Composable
internal fun GithubAuthRoute(
    viewModel: GithubAuthViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = uiState) {
        if (uiState is GithubAuthUiState.Success) {
            delay(timeMillis = SUCCESS_AUTO_POP_DELAY_MS)
            popBackStack()
        }
    }

    GithubAuthContent(
        uiState = uiState,
        onOpenGithubClick = { url -> openUrl(context = context, url = url) },
        onDisconnectClick = viewModel::disconnect,
        onRetryClick = viewModel::retry,
        popBackStack = popBackStack
    )
}

@Composable
private fun GithubAuthContent(
    uiState: GithubAuthUiState,
    onOpenGithubClick: (String) -> Unit,
    onDisconnectClick: () -> Unit,
    onRetryClick: () -> Unit,
    popBackStack: () -> Unit,
) {
    GithubAuthScreen(
        uiState = uiState,
        onOpenGithubClick = onOpenGithubClick,
        onDisconnectClick = onDisconnectClick,
        onRetryClick = onRetryClick,
        popBackStack = popBackStack
    )
}