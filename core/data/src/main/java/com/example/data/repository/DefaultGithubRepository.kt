package com.example.data.repository

import com.example.data.remote.github.GithubDeviceCodeApi
import com.example.data.remote.github.GithubGraphQlApi
import com.example.data_api.repository.GithubRepository
import com.example.datastore.datasource.GithubTokenDataSource
import com.example.model.github.GithubContributionDay
import com.example.model.github.GithubDeviceCode
import com.example.model.github.GithubPollResult
import kotlinx.coroutines.flow.Flow

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
            )
        }
        return result
    }

    override suspend fun getContributionDays(): List<GithubContributionDay> {
        val accessToken = tokenDataSource.getAccessToken() ?: return emptyList()
        val (_, days) =
            graphQlApi.fetchContributionCalendar(
                accessToken = accessToken,
            )
        return days
    }

    override suspend fun disconnect() = tokenDataSource.clear()
}
