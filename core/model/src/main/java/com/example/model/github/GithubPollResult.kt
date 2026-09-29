package com.example.model.github

sealed interface GithubPollResult {
    data object Pending : GithubPollResult

    data object SlowDown : GithubPollResult

    data class Success(
        val accessToken: String,
        val username: String,
        val refreshToken: String?,
        val accessTokenExpiresInSeconds: Int?,
    ) : GithubPollResult

    data object Expired : GithubPollResult

    data object Denied : GithubPollResult
}
