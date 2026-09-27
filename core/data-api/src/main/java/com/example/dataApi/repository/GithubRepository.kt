package com.example.dataApi.repository

import com.example.model.github.GithubContributionDay
import com.example.model.github.GithubDeviceCode
import com.example.model.github.GithubPollResult
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getGithubUsername(): Flow<String?>
    suspend fun requestDeviceCode(): GithubDeviceCode
    suspend fun pollAccessToken(deviceCode: String): GithubPollResult
    suspend fun getContributionDays(): List<GithubContributionDay>
    suspend fun disconnect()
}
