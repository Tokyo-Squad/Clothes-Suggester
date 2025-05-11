package data.remote

import data.remote.dto.LocationResponse
import data.remote.dto.WeatherResponseDto

interface WeatherApi {
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherResponseDto
    suspend fun geocodeCity(city: String): LocationResponse
}