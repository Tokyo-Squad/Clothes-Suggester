package logic.model

data class Weather(
    val temperature: Double,
    val condition: WeatherCondition,
    val windSpeed: Double,
)

enum class WeatherCondition {
    Clear, Cloudy, Overcast, Rainy, Drizzle,
    Showers, Snowy, Sleet,  Stormy,
    Foggy, Unknown
}