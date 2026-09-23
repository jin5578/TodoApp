package com.example.github_auth.model

sealed interface GithubAuthUiState {
    data object Loading : GithubAuthUiState

    data class Connected(val username: String) : GithubAuthUiState

    data class AwaitingUser(
        val userCode: String,
        val verificationUri: String,
        val isPolling: Boolean,
    ) : GithubAuthUiState

    data object Success : GithubAuthUiState

    data class Error(val reason: GithubAuthErrorReason) : GithubAuthUiState
}
