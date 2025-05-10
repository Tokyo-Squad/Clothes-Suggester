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
    private val coroutineScope = CoroutineScope(Dispatchers.Main + createExceptionHandler())

    private fun createExceptionHandler() = CoroutineExceptionHandler { _, exception ->
        console.writeError("An error occurred: ${exception.message}")
        showMenu()
    }

    fun start() {
        console.write("Welcome to clothes suggestion")
        showMenu()
    }

    private fun showMenu() {
        try {
            displayMenuOptions()
            handleMenuChoice()
        } catch (e: Exception) {
            console.writeError("Menu error: ${e.message}")
            showMenu()
        }
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

    private fun handleMenuChoice() {
        when (console.read()) {
            "1" -> launchWithExceptionHandling { getClothingSuggestion() }
            "2" -> launchWithExceptionHandling { getClothingSuggestionForCurrentLocation() }
            "3" -> handleExit()
            else -> handleInvalidChoice()
        }
    }

    private fun handleExit() {
        console.write("Exiting the application. Have a nice day!")
        coroutineScope.cancel()
    }

    private fun handleInvalidChoice() {
        console.writeError("Invalid choice. Please try again.")
        showMenu()
    }

    private fun launchWithExceptionHandling(block: suspend () -> Unit) {
        coroutineScope.launch {
            try {
                block()
            } catch (e: Exception) {
                console.writeError("Operation failed: ${e.message}")
            } finally {
                showMenu()
            }
        }
    }

    private suspend fun getClothingSuggestion() {
        console.write("Enter city name: ")
        val city = console.read()

        if (city.isEmpty()) {
            console.writeError("City name is required.")
            return
        }

        val suggestion = withContext(Dispatchers.IO) {
            getClothesSuggestionUseCase(city)
        }

        displaySuggestion(city, suggestion)
    }

    private suspend fun getClothingSuggestionForCurrentLocation() {
        console.write("Detecting your current location...")

        val currentCity = withContext(Dispatchers.IO) {
            getCurrentLocationUseCase()
        }

        if (currentCity.isEmpty()) {
            console.writeError("Could not detect your location. Please try entering a city manually.")
            getClothingSuggestion()
            return
        }

        handleDetectedLocation(currentCity)
    }

    private suspend fun handleDetectedLocation(currentCity: String) {
        console.write("Detected location: $currentCity")
        console.write("Do you want to use this location? (y/n)")

        when (console.read().lowercase()) {
            "y", "yes" -> {
                val suggestion = withContext(Dispatchers.IO) {
                    getClothesSuggestionUseCase(currentCity)
                }
                displaySuggestion(currentCity, suggestion)
            }
            else -> {
                console.write("Let's enter a city manually instead.")
                getClothingSuggestion()
            }
        }
    }

    private fun displaySuggestion(city: String, suggestion: ClothingSuggestion) {
        try {
            console.write("""
                Weather in $city:
                Temperature: ${suggestion.weather.temperature}°C
                Condition: ${suggestion.weather.condition}
                Wind Speed: ${suggestion.weather.windSpeed} m/s
                
                Recommended Clothing:
                ${suggestion.clothItems.joinToString("\n") { "- $it" }}
            """.trimIndent())
        } catch (e: Exception) {
            console.writeError("Error displaying suggestion: ${e.message}")
        }
    }
}