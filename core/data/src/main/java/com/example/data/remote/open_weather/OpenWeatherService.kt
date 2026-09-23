package com.example.data.remote.open_weather

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
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
    val main: Main,
    val wind: Wind,
    val name: String,
)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class Main(
    val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp_max") val tempMax: Double,
    val pressure: Double,
    val humidity: Double,
    @SerialName("sea_level") val seaLevel: Int,
    @SerialName("grnd_level") val groundLevel: Int,
)

@OptIn(InternalSerializationApi::class)
@Serializable
internal data class Wind(
    val speed: Double,
    val deg: Int,
)
