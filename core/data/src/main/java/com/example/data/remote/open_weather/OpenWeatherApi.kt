package com.example.data.remote.open_weather

import com.example.model.open_weather.OpenWeather
import com.example.model.open_weather.OpenWeatherMain
import com.example.model.open_weather.OpenWeatherWind
import com.example.model.open_weather.WeatherInfo

internal class OpenWeatherApi(
    private val service: OpenWeatherService,
    private val appid: String,
) {
    suspend fun getWeather(
        lat: Double,
        lon: Double,
    ): WeatherInfo {
        val response = service.getWeather(
            lat = lat,
            lon = lon,
            appid = appid,
        )

        return WeatherInfo(
            weather = response.weather.getOrNull(index = 0)?.toOpenWeather(),
            main = response.main.toOpenWeatherMain(),
            wind = response.wind.toOpenWeatherWind(),
            name = response.name,
        )
    }

    private fun Weather.toOpenWeather() = OpenWeather(
        id = this.id,
        main = this.main,
        description = this.description,
        icon = this.icon,
    )

    private fun Main.toOpenWeatherMain() = OpenWeatherMain(
        temp = this.temp,
        feelsLike = this.feelsLike,
        tempMin = this.tempMin,
        tempMax = this.tempMax,
        pressure = this.pressure,
        humidity = this.humidity,
    )

    private fun Wind.toOpenWeatherWind() = OpenWeatherWind(
        speed = this.speed,
        deg = this.deg,
    )
}