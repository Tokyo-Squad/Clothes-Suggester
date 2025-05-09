package logic.model

import logic.model.Weather

data class ClothingSuggestion(
    val clothItems:List<String>,
    val weather: Weather
)

