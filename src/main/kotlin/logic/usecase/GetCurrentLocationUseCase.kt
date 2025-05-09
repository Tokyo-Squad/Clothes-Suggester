package logic.usecase

import logic.repository.WeatherRepository


class GetCurrentLocationUseCase(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(): String {
        return weatherRepository.getCurrentCity()
    }
}