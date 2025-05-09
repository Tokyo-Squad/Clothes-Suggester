package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import logic.repository.WeatherRepository
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetCurrentLocationUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @BeforeTest
    fun setUp() {
        weatherRepository = mockk(relaxed = true)
        getCurrentLocationUseCase = GetCurrentLocationUseCase(weatherRepository)
    }

    @Test
    fun `should return current city when repository returns valid city`() = runTest {
        // Given
        val expectedCity = "Berlin"
        coEvery { weatherRepository.getCurrentCity() } returns expectedCity

        // When
        val result = getCurrentLocationUseCase()

        // Then
        assertThat(result).isEqualTo(expectedCity)
        coVerify(exactly = 1) { weatherRepository.getCurrentCity() }
    }

    @Test
    fun `should return city name with whitespace and special characters`() = runTest {
        // Given
        val expectedCity = "São Paulo"
        coEvery { weatherRepository.getCurrentCity() } returns expectedCity

        // When
        val result = getCurrentLocationUseCase()

        // Then
        assertThat(result).isEqualTo(expectedCity)
        coVerify(exactly = 1) { weatherRepository.getCurrentCity() }
    }

}
