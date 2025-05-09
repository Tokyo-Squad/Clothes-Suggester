package data.mapper

import data.remote.dto.WeatherResponseDto
import logic.model.Weather
import logic.model.WeatherCondition


fun WeatherResponseDto.toWeather(): Weather =
    Weather(
        temperature = current.temperature,
        condition = mapCodeToCondition(current.weathercode),
        windSpeed = current.windspeed,
    )


private fun mapCodeToCondition(code: Int) = when (code) {
    0  -> WeatherCondition.Clear
    1  -> WeatherCondition.Cloudy
    2,3-> WeatherCondition.Overcast
    in 45..48 -> WeatherCondition.Foggy
    in 51..55 -> WeatherCondition.Drizzle
    in 56..57 -> WeatherCondition.Sleet
    in 61..65 -> WeatherCondition.Rainy
    in 66..67, in 71..75 -> WeatherCondition.Snowy
    in 80..82 -> WeatherCondition.Showers
    in 95..99 -> WeatherCondition.Stormy
    else -> WeatherCondition.Unknown
}