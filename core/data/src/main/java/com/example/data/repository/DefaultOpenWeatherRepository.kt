package com.example.data.repository

import com.example.data.remote.open_weather.OpenWeatherApi
import com.example.data_api.repository.OpenWeatherRepository
import com.example.model.open_weather.OpenWeather
import javax.inject.Inject

internal class DefaultOpenWeatherRepository @Inject constructor(
    private val openWeatherApi: OpenWeatherApi,
) : OpenWeatherRepository {
    override suspend fun getWeather(
        lat: Double,
        lon: Double,
    ): List<OpenWeather> =
        openWeatherApi.getWeather(
            lat = lat,
            lon = lon
        )
}