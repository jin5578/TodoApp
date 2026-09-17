package com.example.data_api.repository

import com.example.model.open_weather.OpenWeather

interface OpenWeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): List<OpenWeather>
}