package data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    @SerialName("generationtime_ms")
    val generationtimeMs: Double,
    @SerialName("results")
    val locationDtos: List<LocationDto>
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