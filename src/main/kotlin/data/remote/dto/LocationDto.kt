package data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String? = null
)