package logic.usecase

import logic.model.ClothingSuggestion
import logic.repository.WeatherRepository

class GetClothesSuggestionUseCase(
    val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: String): ClothingSuggestion {
        TODO("Implement the use case")
    }
}