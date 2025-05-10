package logic.usecase

import logic.repository.WeatherRepository


class GetCurrentLocationUseCase(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(): String {
        val city = weatherRepository.getCurrentCity().trim()
        validateCityName(city)
        return city
    }

    private fun validateCityName(city: String) {
        if (city.isEmpty()) {
            throw IllegalArgumentException("City name cannot be empty")
        }

        if (!city.all { it.isLetter() || it.isWhitespace() }) {
            throw IllegalArgumentException("City name contains invalid characters")
        }
    }
}