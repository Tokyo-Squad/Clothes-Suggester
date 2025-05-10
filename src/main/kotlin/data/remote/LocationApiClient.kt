package data.remote

import data.remote.dto.IpInfoDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import utils.LocationApiException

class LocationApiClient(
    private val client: HttpClient
) : LocationApi {

    override suspend fun getCurrentCity(): String =
        try {
            val dto: IpInfoDto = client.get("https://ipinfo.io/json").body()
            dto.city
        } catch (e: ResponseException) {
            throw LocationApiException("Failed to location city: ${e.response.status}")
        } catch (e: Exception) {
            throw LocationApiException("Unexpected error while geocoding city")
        }
}