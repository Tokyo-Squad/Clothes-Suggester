package data.repository

import data.remote.dto.IpCoordinate

interface LocationRepository {
    suspend fun getCurrentLocation(): IpCoordinate
}