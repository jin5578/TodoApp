package com.example.data.remote.open_weather

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

internal interface OpenWeatherService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units: String = "metric",
    ): WeatherResponse
}

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class WeatherResponse(
    val weather: List<Weather>,
)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)