package com.example.domain

import com.example.dataApi.repository.OpenWeatherRepository
import javax.inject.Inject

class GetOpenWeatherUseCase
@Inject
constructor(
    private val openWeatherRepository: OpenWeatherRepository,
) {
    suspend operator fun invoke(
        lat: Double,
        lon: Double,
    ) = openWeatherRepository.getWeather(lat = lat, lon = lon)
}
