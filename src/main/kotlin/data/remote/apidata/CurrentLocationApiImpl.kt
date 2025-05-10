package data.remote.apidata

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class CurrentLocationApiImpl(
    private val httpClient: HttpClient,
    private val url: String = "https://ipinfo.io/json"
) : CurrentLocationApi {

    override suspend fun fetchLocationJson(): String =
        httpClient.get(url).bodyAsText()
}