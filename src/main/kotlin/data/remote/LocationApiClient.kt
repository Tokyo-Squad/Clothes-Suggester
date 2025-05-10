package data.remote

import data.remote.dto.IpInfoDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import utils.LocationApiException

class LocationApiClient : LocationApi {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) { json() }
        expectSuccess = true
    }

    override suspend fun getCurrentCity(): String {
        try {
            val dto: IpInfoDto = client.get("https://ipinfo.io/json") {
                accept(ContentType.Application.Json)
            }.body()

            return dto.city
        } catch (e: ResponseException) {
            throw LocationApiException("Failed to location city: ${e.response.status}")
        } catch (e: Exception) {
            throw LocationApiException("Unexpected error while geocoding city")
        }
    }

}