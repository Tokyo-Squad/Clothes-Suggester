package data.remote.apidata

import data.remote.dto.IpCoordinate
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class IpLocationParserImpl : IpLocationParser {
    private val jsonParser = Json { ignoreUnknownKeys = true }

    companion object {
        private const val KEY_LOCATION = "loc"
    }

    override fun parse(body: String): IpCoordinate {
        val json = jsonParser
            .parseToJsonElement(body)
            .jsonObject

        val locationString = json[KEY_LOCATION]
            ?.jsonPrimitive
            ?.content
            ?: throw IllegalStateException("Field '$KEY_LOCATION' not found in Json response")

        val (latitude, longitude) = locationString
            .split(",")
            .map(String::trim)
            .map(String::toDouble)

        return IpCoordinate(
            longitude = latitude,
            latitude = longitude
        )
    }
}
