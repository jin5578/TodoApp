package com.example.data.repository

import com.example.data.remote.github.GithubDeviceCodeApi
import com.example.data.remote.github.GithubGraphQlApi
import com.example.dataApi.repository.GithubRepository
import com.example.datastore.datasource.GithubTokenDataSource
import com.example.model.github.GithubContributionDay
import com.example.model.github.GithubDeviceCode
import com.example.model.github.GithubPollResult
import com.example.model.github.GithubTokenRefreshResult
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

private const val HTTP_UNAUTHORIZED = 401

internal class DefaultGithubRepository(
    private val deviceCodeApi: GithubDeviceCodeApi,
    private val graphQlApi: GithubGraphQlApi,
    private val tokenDataSource: GithubTokenDataSource,
) : GithubRepository {
    override fun getGithubUsername(): Flow<String?> = tokenDataSource.githubUsername

    override suspend fun requestDeviceCode(): GithubDeviceCode = deviceCodeApi.requestDeviceCode()

    override suspend fun pollAccessToken(deviceCode: String): GithubPollResult {
        val result =
            deviceCodeApi.pollAccessToken(
                deviceCode = deviceCode,
            )
        if (result is GithubPollResult.Success) {
            tokenDataSource.saveToken(
                accessToken = result.accessToken,
                username = result.username,
                refreshToken = result.refreshToken,
                accessTokenExpiresInSeconds = result.accessTokenExpiresInSeconds,
            )
        }
        return result
    }

    override suspend fun getContributionDays(): List<GithubContributionDay> {
        val accessToken = ensureValidAccessToken() ?: return emptyList()
        return try {
            graphQlApi.fetchContributionCalendar(accessToken = accessToken).second
        } catch (e: HttpException) {
            if (e.code() != HTTP_UNAUTHORIZED) throw e
            val refreshedAccessToken =
                ensureValidAccessToken(forceRefresh = true)
                    ?: return emptyList()
            graphQlApi.fetchContributionCalendar(accessToken = refreshedAccessToken).second
        }
    }

    override suspend fun disconnect() = tokenDataSource.clear()

    private suspend fun ensureValidAccessToken(forceRefresh: Boolean = false): String? {
        if (!forceRefresh && !tokenDataSource.isAccessTokenExpired()) {
            return tokenDataSource.getAccessToken()
        }

        val refreshToken = tokenDataSource.getRefreshToken()
            ?: return tokenDataSource.getAccessToken()

        return when (
            val result =
                deviceCodeApi.refreshAccessToken(refreshToken = refreshToken)
        ) {
            is GithubTokenRefreshResult.Success -> {
                tokenDataSource.updateAccessToken(
                    accessToken = result.accessToken,
                    refreshToken = result.refreshToken ?: refreshToken,
                    accessTokenExpiresInSeconds = result.accessTokenExpiresInSeconds,
                )
                result.accessToken
            }

            GithubTokenRefreshResult.Invalid -> {
                tokenDataSource.clear()
                null
            }
        }
    }
}
