package data.repository

import data.remote.repository.WeatherApi
import logic.model.Weather
import logic.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
) : WeatherRepository {
    override suspend fun getCurrentWeather(city: String): Weather {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentCity(): String {
        TODO("Not yet implemented")
    }


}