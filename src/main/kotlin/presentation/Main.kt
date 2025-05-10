package presentation

import data.remote.WeatherApiClient
import di.appModule
import di.repositoryModule
import di.uiModule
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin


fun main() {
    startKoin {
        modules(
            appModule, uiModule, repositoryModule
        )
    }
        val clothesSuggesterUI: ClothesSuggesterUI = getKoin().get()
        runBlocking {
    clothesSuggesterUI.start()
    }
}