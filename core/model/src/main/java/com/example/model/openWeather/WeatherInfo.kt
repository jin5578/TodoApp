package com.example.model.openWeather

data class WeatherInfo(
    val weather: OpenWeather?,
    val main: OpenWeatherMain,
    val wind: OpenWeatherWind,
    val name: String,
)

data class OpenWeather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
)

data class OpenWeatherMain(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Double,
    val humidity: Double,
)

data class OpenWeatherWind(
    val speed: Double,
    val deg: Int,
)
