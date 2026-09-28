package com.example.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface GithubTokenDataSource {
    val githubUsername: Flow<String?>

    suspend fun saveToken(
        accessToken: String,
        username: String,
        refreshToken: String?,
        accessTokenExpiresInSeconds: Int?,
    )

    suspend fun updateAccessToken(
        accessToken: String,
        refreshToken: String?,
        accessTokenExpiresInSeconds: Int?,
    )

    suspend fun clear()

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun isAccessTokenExpired(): Boolean
}
