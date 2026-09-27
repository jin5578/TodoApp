package com.example.data.repository

import com.example.data.remote.openWeather.OpenWeatherApi
import com.example.dataApi.repository.OpenWeatherRepository
import com.example.model.openWeather.WeatherInfo
import javax.inject.Inject

internal class DefaultOpenWeatherRepository
@Inject
constructor(
    private val openWeatherApi: OpenWeatherApi,
) : OpenWeatherRepository {
    override suspend fun getWeather(
        lat: Double,
        lon: Double,
    ): WeatherInfo = openWeatherApi.getWeather(
        lat = lat,
        lon = lon,
    )
}
