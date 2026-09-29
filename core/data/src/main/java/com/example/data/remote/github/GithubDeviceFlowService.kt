package com.example.data.remote.github

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface GithubDeviceFlowService {
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login/device/code")
    suspend fun requestDeviceCode(
        @Field("client_id") clientId: String,
        @Field("scope") scope: String,
    ): DeviceCodeResponse

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    suspend fun requestAccessToken(
        @Field("client_id") clientId: String,
        @Field("device_code") deviceCode: String,
        @Field("grant_type") grantType: String,
    ): AccessTokenResponse

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    suspend fun refreshAccessToken(
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String,
    ): AccessTokenResponse
}

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class DeviceCodeResponse(
    @SerialName("device_code") val deviceCode: String,
    @SerialName("user_code") val userCode: String,
    @SerialName("verification_uri") val verificationUri: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("interval") val interval: Int,
)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class AccessTokenResponse(
    @SerialName("access_token") val accessToken: String? = null,
    @SerialName("expires_in") val expiresInSeconds: Int? = null,
    @SerialName("refresh_token") val refreshToken: String? = null,
    @SerialName("error") val error: String? = null,
)
