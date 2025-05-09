package data.repository

import data.mapper.toWeather
import data.remote.WeatherApi
import logic.model.Weather
import logic.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
) : WeatherRepository {

    override suspend fun getCurrentWeather(city: String): Weather {
        val locationDto = weatherApi.geocodeCity(city)
        return weatherApi.getCurrentWeather(locationDto.latitude, locationDto.longitude).toWeather()
    }

    override suspend fun getCurrentCity(): String {
        TODO("Not yet implemented")
    }


}