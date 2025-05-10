package utils

open class ClothesSuggestionException(message: String) : Exception(message)

class WeatherApiException(message: String) : ClothesSuggestionException(message)

class GeocodingException(message: String) : ClothesSuggestionException(message)

class InvalidCityNameException(message: String) : ClothesSuggestionException(message)

class LocationApiException(message: String) : ClothesSuggestionException(message)