package data.remote

interface LocationApi {
    suspend fun getCurrentCity():String
}