package di

import data.remote.LocationApi
import data.remote.LocationApiClient
import data.remote.WeatherApi
import data.remote.WeatherApiClient
import data.repository.WeatherRepositoryImpl
import logic.repository.WeatherRepository
import logic.usecase.GetClothesSuggestionUseCase
import logic.usecase.GetCurrentLocationUseCase
import org.koin.dsl.module

val appModule = module {
    single { GetClothesSuggestionUseCase(get()) }
    single { GetCurrentLocationUseCase(get()) }

}
val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(
        get(),
        get()
    ) }
    single<WeatherApi> { WeatherApiClient() }
    single<LocationApi>{LocationApiClient()}
}