package data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    @SerialName("country_code")
    val countryCode: String,
    val timezone: String
)