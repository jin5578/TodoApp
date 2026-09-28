package com.example.model.github

sealed interface GithubTokenRefreshResult {
    data class Success(
        val accessToken: String,
        val refreshToken: String?,
        val accessTokenExpiresInSeconds: Int?,
    ) : GithubTokenRefreshResult

    data object Invalid : GithubTokenRefreshResult
}
