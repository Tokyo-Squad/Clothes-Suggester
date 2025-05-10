package presentation

import kotlinx.coroutines.*
import logic.model.ClothingSuggestion
import logic.usecase.GetClothesSuggestionUseCase
import logic.usecase.GetCurrentLocationUseCase
import presentation.io.ConsoleIO
class ClothesSuggesterUI(
    private val console: ConsoleIO,
    private val getClothesSuggestionUseCase: GetClothesSuggestionUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) {
    suspend fun start() {
        console.write("Welcome to clothes suggestion")
        var isRunning = true

        while (isRunning) {
            displayMenuOptions()
            when (console.read()) {
                "1" -> handleClothingSuggestion()
                "2" -> handleLocationSuggestion()
                "3" -> isRunning = false
                else -> console.writeError("Invalid choice. Please try again.")
            }
        }
        console.write("Exiting the application. Have a nice day!")
    }

    private fun displayMenuOptions() {
        console.write("""
            Please select an option:
            1. Get Clothing Suggestion
            2. Get Clothing Suggestion for Current Location
            3. Exit
            Enter your choice: 
        """.trimIndent())
    }

    private suspend fun handleClothingSuggestion() {
        try {
            console.write("Enter city name: ")
            val city = console.read()

            if (city.isEmpty()) {
                console.writeError("City name is required.")
                return
            }

            val suggestion = getClothesSuggestionUseCase(city)
            displaySuggestion(city, suggestion)
        } catch (e: Exception) {
            console.writeError("Failed to get suggestion: ${e.message}")
        }
    }

    private suspend fun handleLocationSuggestion() {
        try {
            console.write("Detecting your current location...")
            val currentCity = getCurrentLocationUseCase()

            if (currentCity.isEmpty()) {
                console.writeError("Could not detect your location. Please try entering a city manually.")
                handleClothingSuggestion()
                return
            }

            console.write("Detected location: $currentCity")
            console.write("Do you want to use this location? (y/n)")

            when (console.read().lowercase()) {
                "y", "yes" -> {
                    val suggestion = getClothesSuggestionUseCase(currentCity)
                    displaySuggestion(currentCity, suggestion)
                }
                else -> {
                    console.write("Let's enter a city manually instead.")
                    handleClothingSuggestion()
                }
            }
        } catch (e: Exception) {
            console.writeError("Failed to get location: ${e.message}")
        }
    }

    private fun displaySuggestion(city: String, suggestion: ClothingSuggestion) {
        console.write("""
            Weather in $city:
            Temperature: ${suggestion.weather.temperature}°C
            Condition: ${suggestion.weather.condition}
            Wind Speed: ${suggestion.weather.windSpeed} m/s
            
            Recommended Clothing:
            ${suggestion.clothItems.joinToString("\n") { "- $it" }}
        """.trimIndent())
    }
}