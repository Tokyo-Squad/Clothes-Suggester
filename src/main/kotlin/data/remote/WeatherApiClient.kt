package data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import data.remote.dto.LocationDto
import data.remote.dto.WeatherResponseDto

class WeatherApiClient : WeatherApi {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
    }

    override suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherResponseDto {
        return client.get("https://api.open-meteo.com/v1/forecast") {
            parameter("latitude", lat)
            parameter("longitude", lon)
            parameter("current_weather", true)
        }.body()
    }

    override suspend fun geocodeCity(city: String): LocationDto {
        return client.get("https://geocoding-api.open-meteo.com/v1/search") {
            parameter("name", city)
            parameter("count", 1)
        }.body()
    }

}