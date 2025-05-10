package data.remote.apidata

import data.remote.dto.LocationDto
import data.remote.dto.WeatherResponseDto

interface WeatherApi {
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherResponseDto
    suspend fun geocodeCity(city: String): LocationDto
}