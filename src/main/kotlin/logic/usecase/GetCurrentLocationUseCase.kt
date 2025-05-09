package logic.usecase

import data.remote.repository.WeatherApi

class GetCurrentLocationUseCase(
    private val weatherApi: WeatherApi,
) {
    suspend operator fun invoke(): String {
        TODO("Implement the use case")
    }
}