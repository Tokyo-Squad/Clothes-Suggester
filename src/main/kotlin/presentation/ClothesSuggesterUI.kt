package presentation

import kotlinx.coroutines.runBlocking
import logic.usecase.GetClothesSuggestionUseCase
import presentation.io.ConsoleIO

class ClothesSuggesterUI(
    private val console: ConsoleIO,
    private val getClothesSuggestionUseCase: GetClothesSuggestionUseCase
) {

    fun start(){
        console.write("Welcome to clothes suggestion")
        showMenu()
    }

    private fun showMenu() {
        console.write("Please select an option:")
        console.write("1. Get Clothing Suggestion")
        console.write("2. Exit") // Added exit option
        console.write("Enter your choice: ")
        when (console.read()) {
            "1" -> getClothingSuggestion()
            "2" -> console.write("Exiting the application. Have a nice day!")
            else -> {
                console.write("Invalid choice. Please try again.")
                showMenu()
            }
        }
    }

    fun getClothingSuggestion() {
        // Get user input (city)
        print("Enter city name: ")
        val city = console.read()

        if (city.isEmpty()) {
            println("City name is required.")
            return
        }
        runBlocking {
            try {
                val suggestion = getClothesSuggestionUseCase(city)

                console.write("Weather in $city:")
                console.write("Temperature: ${suggestion.weather.temperature}°C")
                console.write("Condition: ${suggestion.weather.condition}")
                console.write("Wind Speed: ${suggestion.weather.windSpeed} m/s")
                console.write("\nRecommended Clothing:")
                suggestion.clothItems.forEach { item ->
                    console.write("- $item")
                }
            } catch (e: Exception) {
                console.write("Error: ${e.message}")
            }
        }
    }
}