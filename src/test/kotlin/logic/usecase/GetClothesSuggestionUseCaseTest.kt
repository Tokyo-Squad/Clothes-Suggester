package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.model.ClothingSuggestion
import logic.model.Weather
import logic.model.WeatherCondition
import logic.repository.WeatherRepository
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetClothesSuggestionUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getClothesSuggestionUseCase: GetClothesSuggestionUseCase

    @BeforeEach
    fun setUp() {
        weatherRepository = mockk()
        getClothesSuggestionUseCase = GetClothesSuggestionUseCase(weatherRepository)
    }

    @Test
    fun `should return clothing suggestions for cold weather when temperature is below zero`() = runTest {
        // Given
        val city = "Test City"
        val weather = Weather(temperature = -5.0, condition = WeatherCondition.Snowy, windSpeed = 10.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        // When
        val result: ClothingSuggestion = getClothesSuggestionUseCase(city)

        // Then
        assertThat(result.clothItems).containsExactly(
            "Heavy coat",
            "Warm scarf",
            "Gloves",
            "Thermal wear",
            "Snow boots",
            "Wool socks",
            "Winter jacket"
        )
        assertThat(result.weather).isEqualTo(weather)
    }

    @Test
    fun `should return clothing suggestions for mild weather when temperature is between 0 and 15 degrees`() = runTest {
        // Given
        val city = "Test City"
        val weather = Weather(temperature = 10.0, condition = WeatherCondition.Cloudy, windSpeed = 10.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        // When
        val result: ClothingSuggestion = getClothesSuggestionUseCase(city)

        // Then
        assertThat(result.clothItems).containsExactly(
            "Light jacket",
            "Sweater",
            "Closed shoes",
            "Comfortable shoes"
        )
        assertThat(result.weather).isEqualTo(weather)
    }

    @Test
    fun `should return mid-range clothing when temperature is exactly 25`() = runTest {
        val city = "Test City"
        val weather = Weather(temperature = 25.0, condition = WeatherCondition.Cloudy, windSpeed = 5.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        val result = getClothesSuggestionUseCase(city)

        assertThat(result.clothItems).containsExactly("Long-sleeve shirt", "Comfortable trousers")
    }

    @Test
    fun `should return clothing suggestions for hot weather when temperature is above 25 degrees`() = runTest {
        // Given
        val city = "Test City"
        val weather = Weather(temperature = 30.0, condition = WeatherCondition.Clear, windSpeed = 5.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        // When
        val result: ClothingSuggestion = getClothesSuggestionUseCase(city)

        // Then
        assertThat(result.clothItems).containsExactly("Short-sleeve shirt", "Shorts", "Light skirt", "Cap")
        assertThat(result.weather).isEqualTo(weather)
    }

    @Test
    fun `should return cold weather clothing when temperature is exactly 0 degrees`() = runTest {
        val city = "Test City"
        val weather = Weather(temperature = 0.0, condition = WeatherCondition.Cloudy, windSpeed = 5.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        val result = getClothesSuggestionUseCase(city)

        assertThat(result.clothItems).containsAtLeast("Light jacket", "Sweater", "Closed shoes")
        assertThat(result.weather).isEqualTo(weather)
    }

    @Test
    fun `should return mild weather clothing when temperature is exactly 15 degrees`() = runTest {
        val city = "Test City"
        val weather = Weather(temperature = 15.0, condition = WeatherCondition.Cloudy, windSpeed = 5.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        val result = getClothesSuggestionUseCase(city)

        assertThat(result.clothItems).containsAtLeast("Light jacket", "Sweater", "Closed shoes")
        assertThat(result.weather).isEqualTo(weather)
    }

    @Test
    fun `should return mid-range clothing when temperature is just above 15 degrees`() = runTest {
        val city = "Test City"
        val weather = Weather(temperature = 15.1, condition = WeatherCondition.Cloudy, windSpeed = 5.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        val result = getClothesSuggestionUseCase(city)

        assertThat(result.clothItems).containsExactly("Long-sleeve shirt", "Comfortable trousers")
        assertThat(result.weather).isEqualTo(weather)
    }

    @Test
    fun `should return summer clothing when temperature is just above 25 degrees`() = runTest {
        val city = "Test City"
        val weather = Weather(temperature = 25.1, condition = WeatherCondition.Clear, windSpeed = 5.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        val result = getClothesSuggestionUseCase(city)

        assertThat(result.clothItems).containsExactly("Short-sleeve shirt", "Shorts", "Light skirt", "Cap")
        assertThat(result.weather).isEqualTo(weather)
    }

    @Test
    fun `should return clothing suggestions for windy weather when wind speed is greater than 30 km_h`() = runTest {
        // Given
        val city = "Test City"
        val weather = Weather(temperature = 20.0, condition = WeatherCondition.Cloudy, windSpeed = 35.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        // When
        val result: ClothingSuggestion = getClothesSuggestionUseCase(city)

        // Then
        assertThat(result.clothItems).containsExactly(
            "Long-sleeve shirt",
            "Comfortable trousers",
            "Light jacket",
            "Comfortable shoes",
            "Wind-resistant jacket"
        )
        assertThat(result.weather).isEqualTo(weather)
    }

    @Test
    fun `should return empty list when wind speed is less than or equal to 30 km h`() = runTest {
        // Given
        val city = "Test City"
        val weather = Weather(temperature = 20.0, condition = WeatherCondition.Cloudy, windSpeed = 25.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        // When
        val result: ClothingSuggestion = getClothesSuggestionUseCase(city)

        // Then
        assertThat(result.clothItems).doesNotContain("Wind-resistant jacket")
        assertThat(result.weather).isEqualTo(weather)
    }

    @Test
    fun `should return clothing suggestions for rainy weather when weather is rainy`() = runTest {
        // Given
        val city = "Test City"
        val weather = Weather(temperature = 18.0, condition = WeatherCondition.Rainy, windSpeed = 5.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        // When
        val result: ClothingSuggestion = getClothesSuggestionUseCase(city)

        // Then
        assertThat(result.clothItems).containsExactly(
            "Light jacket",
            "Sweater",
            "Closed shoes",
            "Umbrella",
            "Raincoat",
            "Waterproof shoes"
        )
        assertThat(result.weather).isEqualTo(weather)
    }

    @Test
    fun `should return empty clothing suggestions when weather condition is clear`() = runTest {
        // Given
        val city = "Test City"
        val weather = Weather(temperature = 25.0, condition = WeatherCondition.Clear, windSpeed = 5.0)
        coEvery { weatherRepository.getCurrentWeather(city) } returns weather

        // When
        val result: ClothingSuggestion = getClothesSuggestionUseCase(city)

        // Then
        assertThat(result.clothItems).isEmpty()
        assertThat(result.weather).isEqualTo(weather)
    }

}