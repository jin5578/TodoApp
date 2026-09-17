package com.example.data.remote.open_weather

import com.example.model.open_weather.OpenWeather

internal class OpenWeatherApi(
    private val service: OpenWeatherService,
    private val appid: String,
) {
    suspend fun getWeather(
        lat: Double,
        lon: Double,
    ): List<OpenWeather> {
        val response = service.getWeather(
            lat = lat,
            lon = lon,
            appid = appid,
        )

        return response.weather.map {
            OpenWeather(
                id = it.id,
                main = it.main,
                description = it.description,
                icon = "https://openweathermap.org/payload/api/media/file/${it.icon}.png",
            )
        }
    }
}