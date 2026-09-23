package com.example.data.remote.github

import com.example.model.github.GithubDeviceCode
import com.example.model.github.GithubPollResult

private const val DEVICE_FLOW_GRANT_TYPE =
    "urn:ietf:params:oauth:grant-type:device_code"
private const val DEVICE_FLOW_SCOPE = "read:user"

internal class GithubDeviceCodeApi(
    private val deviceFlowService: GithubDeviceFlowService,
    private val apiService: GithubApiService,
    private val clientId: String,
) {
    suspend fun requestDeviceCode(): GithubDeviceCode {
        val response =
            deviceFlowService.requestDeviceCode(
                clientId = clientId,
                scope = DEVICE_FLOW_SCOPE,
            )

        return GithubDeviceCode(
            deviceCode = response.deviceCode,
            userCode = response.userCode,
            verificationUri = response.verificationUri,
            expiresInSeconds = response.expiresIn,
            intervalSeconds = response.interval,
        )
    }

    suspend fun pollAccessToken(deviceCode: String): GithubPollResult {
        val response =
            deviceFlowService.requestAccessToken(
                clientId = clientId,
                deviceCode = deviceCode,
                grantType = DEVICE_FLOW_GRANT_TYPE,
            )
        val accessToken = response.accessToken

        if (accessToken != null) {
            val username =
                apiService
                    .getUser(
                        authorization = "Bearer $accessToken",
                    ).login
            return GithubPollResult.Success(
                accessToken = accessToken,
                username = username,
            )
        }

        return when (response.error) {
            "authorization_pending" -> GithubPollResult.Pending
            "slow_down" -> GithubPollResult.SlowDown
            "access_denied" -> GithubPollResult.Denied
            else -> GithubPollResult.Expired
        }
    }
}
