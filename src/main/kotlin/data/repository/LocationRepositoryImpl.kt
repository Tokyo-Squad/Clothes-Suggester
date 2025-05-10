package data.repository

import data.remote.apidata.CurrentLocationApi
import data.remote.apidata.IpLocationParser
import data.remote.dto.IpCoordinate
import data.remote.dto.LocationDto
import io.ktor.util.logging.*
import org.slf4j.LoggerFactory.getLogger

class LocationRepositoryImpl(
    private val api: CurrentLocationApi,
    private val parser: IpLocationParser,
    private val logger: Logger = getLogger(LocationRepositoryImpl::class.java)
) : LocationRepository {
    override suspend fun getCurrentLocation(): IpCoordinate =
        try {
            val jsonBody = api.fetchLocationJson()
            parser.parse(jsonBody)
        } catch (e: Exception) {
            logger.error("Error fetching current location", e)
            throw e
        }
}