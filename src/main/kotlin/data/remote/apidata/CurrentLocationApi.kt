package data.remote.apidata

interface CurrentLocationApi {
    suspend fun fetchLocationJson(): String

}