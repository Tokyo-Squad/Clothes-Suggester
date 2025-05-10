package data.remote

import data.remote.dto.LocationDto
import data.remote.dto.SearchResponseDto
import data.remote.dto.WeatherResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import utils.GeocodingException
import utils.WeatherApiException

class WeatherApiClient(
    private val client: HttpClient
) : WeatherApi {

    override suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherResponseDto {
        return try {
            client.get("https://api.open-meteo.com/v1/forecast") {
                parameter("latitude", lat)
                parameter("longitude", lon)
                parameter("current_weather", true)
            }.body()
        } catch (e: ResponseException) {
            throw WeatherApiException("Failed to fetch weather data (HTTP ${e.response.status})")
        } catch (e: Exception) {
            throw WeatherApiException("Unexpected error while fetching weather data: ${e.localizedMessage}")
        }
    }

    override suspend fun geocodeCity(city: String): LocationDto {
        try {
            val search: SearchResponseDto = client.get("https://geocoding-api.open-meteo.com/v1/search") {
                parameter("name", city)
                parameter("count", 1)
            }.body()

            return search.results.firstOrNull()
                ?: throw GeocodingException("No location found for \"$city\"")
        } catch (e: ResponseException) {
            throw GeocodingException("Failed to geocode city (HTTP ${e.response.status})")
        } catch (e: GeocodingException) {
            throw e
        } catch (e: Exception) {
            throw GeocodingException("Unexpected error while geocoding \"$city\": ${e.localizedMessage}")
        }
    }
}