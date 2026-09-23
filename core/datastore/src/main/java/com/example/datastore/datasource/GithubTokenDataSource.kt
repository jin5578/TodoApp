package com.example.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface GithubTokenDataSource {
    val githubUsername: Flow<String?>

    suspend fun saveToken(
        accessToken: String,
        username: String,
    )

    suspend fun clear()

    suspend fun getAccessToken(): String?
}
