package di

import data.remote.*
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
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            get(),
            get()
        )
    }
    single { HttpClientFactory.create() }
    single<WeatherApi> { WeatherApiClient(get()) }
    single<LocationApi> { LocationApiClient(get()) }
}