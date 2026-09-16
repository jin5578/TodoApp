package com.example.data.remote.github

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

internal interface GithubApiService {
    @GET("user")
    suspend fun getUser(
        @Header("Authorization") authorization: String
    ): GithubUserResponse

    @POST("graphql")
    suspend fun postGraphQl(
        @Header("Authorization") authorization: String,
        @Body body: GraphQlRequestBody,
    ): GraphQlResponse
}

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class GithubUserResponse(
    @SerialName("login") val login: String,
)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class GraphQlRequestBody(val query: String)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class GraphQlResponse(val data: GraphQlData? = null)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class GraphQlData(val viewer: GraphQlViewer)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class GraphQlViewer(
    val login: String,
    val contributionsCollection: GraphQlContributionsCollection,
)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class GraphQlContributionsCollection(
    val contributionCalendar: GraphQlContributionCalendar,
)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class GraphQlContributionCalendar(val weeks: List<GraphQlWeek>)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class GraphQlWeek(val contributionDays: List<GraphQlContributionDay>)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class GraphQlContributionDay(
    val date: String,
    val contributionCount: Int,
)