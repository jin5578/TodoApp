package com.example.model.github

sealed interface GithubPollResult {
    data object Pending : GithubPollResult
    data object SlowDown : GithubPollResult
    data class Success(
        val accessToken: String,
        val username: String
    ) : GithubPollResult
    data object Expired : GithubPollResult
    data object Denied : GithubPollResult
}