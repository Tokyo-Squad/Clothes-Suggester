package logic.usecase

import logic.model.ClothingSuggestion
import logic.model.Weather
import logic.model.WeatherCondition
import logic.repository.WeatherRepository

class GetClothesSuggestionUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: String): ClothingSuggestion {
        val weather = weatherRepository.getCurrentWeather(city)
        val clothing = getClothingItems(weather)
        return ClothingSuggestion(clothItems = clothing, weather = weather)
    }

    private fun getClothingItems(weather: Weather): List<String> {
        val items = mutableListOf<String>()
        items += getTemperatureClothing(weather.temperature)
        items += getWeatherConditionClothing(weather.condition)
        items += getWindSpeedClothing(weather.windSpeed)
        return items
    }

    private fun getTemperatureClothing(temperature: Double): List<String> {
        return when {
            temperature < 0 -> listOf("Heavy coat", "Warm scarf", "Gloves", "Thermal wear")
            temperature in 0.0..15.0 -> listOf("Light jacket", "Sweater", "Closed shoes")
            temperature in 15.1..25.0 -> listOf("Long-sleeve shirt", "Comfortable trousers")
            else -> listOf("Short-sleeve shirt", "Shorts", "Light skirt", "Cap")
        }
    }

    private fun getWeatherConditionClothing(condition: WeatherCondition): List<String> {
        return when (condition) {
            WeatherCondition.Rainy, WeatherCondition.Drizzle, WeatherCondition.Showers -> listOf(
                "Umbrella",
                "Raincoat",
                "Waterproof shoes"
            )

            WeatherCondition.Snowy -> listOf("Snow boots", "Wool socks", "Winter jacket")
            WeatherCondition.Stormy -> listOf("Windbreaker", "Stay indoors if possible")
            WeatherCondition.Foggy -> listOf("Wear bright colors for visibility")
            WeatherCondition.Cloudy, WeatherCondition.Overcast -> listOf("Light jacket", "Comfortable shoes")
            WeatherCondition.Clear -> emptyList()
            else -> emptyList()
        }
    }

    private fun getWindSpeedClothing(windSpeed: Double): List<String> {
        return if (windSpeed > 30) listOf("Wind-resistant jacket") else emptyList()
    }
}