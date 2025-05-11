package data.repository

import data.mapper.toWeather
import data.remote.LocationApi
import data.remote.WeatherApi
import logic.model.Weather
import logic.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val locationApi: LocationApi
) : WeatherRepository {

    override suspend fun getCurrentWeather(city: String): Weather {
        val locationDto = weatherApi.geocodeCity(city)
        return weatherApi.getCurrentWeather(locationDto.locationDtos.get(0).latitude, locationDto.locationDtos.get(0).longitude).toWeather()
    }

    override suspend fun getCurrentCity(): String {
        return locationApi.getCurrentCity()
    }


}