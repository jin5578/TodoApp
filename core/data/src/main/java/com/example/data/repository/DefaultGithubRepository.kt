package com.example.data.repository

import com.example.data.remote.github.GithubDeviceCodeApi
import com.example.data.remote.github.GithubGraphQlApi
import com.example.data_api.repository.GithubRepository
import com.example.model.github.GithubContributionDay
import com.example.model.github.GithubDeviceCode
import com.example.model.github.GithubPollResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class DefaultGithubRepository(
    private val deviceCodeApi: GithubDeviceCodeApi,
    private val graphQlApi: GithubGraphQlApi,
) : GithubRepository {
    override fun getGithubUsername(): Flow<String?> {
        //TODO: DataStore 저장
        return flowOf(null)
    }

    override suspend fun requestDeviceCode(): GithubDeviceCode =
        deviceCodeApi.requestDeviceCode()

    override suspend fun pollAccessToken(deviceCode: String): GithubPollResult {
        val result = deviceCodeApi.pollAccessToken(
            deviceCode = deviceCode
        )
        if (result is GithubPollResult.Success) {
            //TODO: DataStore 저장
        }
        return result
    }

    override suspend fun getContributionDays(): List<GithubContributionDay> {
        TODO("Not yet implemented")
    }

    override suspend fun disconnect() {
        //TODO: DataStore 삭제
    }
}