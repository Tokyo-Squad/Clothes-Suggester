package data.remote

import data.remote.dto.LocationResponse
import data.remote.dto.WeatherResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import utils.GeocodingException
import utils.WeatherApiException
import kotlinx.serialization.decodeFromString

class WeatherApiClient : WeatherApi {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
                encodeDefaults = true
            })
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
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
    override suspend fun geocodeCity(city: String): LocationResponse {
         try {
            val response = client.get("https://geocoding-api.open-meteo.com/v1/search") {
                parameter("name", city)
                parameter("count",1)
            }
            return response.body<LocationResponse>()
        } catch (e: ResponseException) {
            throw GeocodingException("Failed to geocode city (HTTP ${e.response.status})")
        } catch (e: SerializationException) {
            throw GeocodingException("Failed to parse geocoding response: ${e.message}")
        } catch (e: Exception) {
            throw GeocodingException("Unexpected error while geocoding city: ${e.message}")
        }
    }
}






