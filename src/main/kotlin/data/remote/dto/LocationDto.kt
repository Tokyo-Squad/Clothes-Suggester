package data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SearchResponseDto(
    @SerialName("generationtime_ms")
    val generationTimeMs: Double = 0.0,
    @SerialName("results")
    val results: List<LocationDto> = emptyList()
)


@Serializable
data class LocationDto(
    val id: Long? = null,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null,
    @SerialName("country_code")
    val countryCode: String? = null,
    val timezone: String? = null
)



@Serializable
data class IpInfoDto(
    val ip: String,
    val city: String,
    val region: String,
    val country: String,
    val loc: String,
    val org: String,
    val timezone: String,
    @SerialName("readme")
    val readmeUrl: String
)