package com.example.data.di

import com.example.data.BuildConfig
import com.example.data.remote.github.GithubApiService
import com.example.data.remote.github.GithubDeviceCodeApi
import com.example.data.remote.github.GithubDeviceFlowService
import com.example.data.remote.github.GithubGraphQlApi
import com.example.data.remote.open_weather.OpenWeatherApi
import com.example.data.remote.open_weather.OpenWeatherService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

private const val GITHUB_AUTH_BASE_URL = "https://github.com/"
private const val GITHUB_API_BASE_URL = "https://api.github.com/"

private const val OPEN_WEATHER_BASE_URL = "https://api.openweathermap.org/"

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    @Singleton
    fun providesJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun providesGithubDeviceFlowService(
        okHttpClient: OkHttpClient,
        json: Json,
    ): GithubDeviceFlowService =
        Retrofit.Builder()
            .baseUrl(GITHUB_AUTH_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GithubDeviceFlowService::class.java)

    @Provides
    @Singleton
    fun providesGithubApiService(
        okHttpClient: OkHttpClient,
        json: Json
    ): GithubApiService =
        Retrofit.Builder()
            .baseUrl(GITHUB_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GithubApiService::class.java)

    @Provides
    @Singleton
    fun providesOpenWeatherService(
        okHttpClient: OkHttpClient,
        json: Json
    ): OpenWeatherService =
        Retrofit.Builder()
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(OpenWeatherService::class.java)

    @Provides
    @Singleton
    fun providesGithubDeviceCodeApi(
        deviceFlowService: GithubDeviceFlowService,
        apiService: GithubApiService,
    ): GithubDeviceCodeApi =
        GithubDeviceCodeApi(
            deviceFlowService = deviceFlowService,
            apiService = apiService,
            clientId = BuildConfig.GITHUB_CLIENT_ID,
        )

    @Provides
    @Singleton
    fun providesGithubGraphQlApi(
        apiService: GithubApiService
    ): GithubGraphQlApi =
        GithubGraphQlApi(apiService = apiService)

    @Provides
    @Singleton
    fun providesOpenWeatherApi(
        service: OpenWeatherService,
    ): OpenWeatherApi =
        OpenWeatherApi(
            service = service,
            appid = BuildConfig.OPEN_WEATHER_APP_ID
        )
}