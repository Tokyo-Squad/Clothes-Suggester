package logic.usecase

import data.remote.apidata.WeatherApi

class GetCurrentLocationUseCase(
    private val weatherApi: WeatherApi,
) {
    suspend operator fun invoke(): String {
        TODO("Implement the use case")
    }
}