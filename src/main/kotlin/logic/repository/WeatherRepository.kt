package logic.repository

import logic.model.Weather

interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): Weather
    suspend fun getCurrentCity(): String
}