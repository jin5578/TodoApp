package com.example.dataApi.repository

import com.example.model.openWeather.WeatherInfo

interface OpenWeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): WeatherInfo
}
