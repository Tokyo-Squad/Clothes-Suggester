package presentation

import di.appModule
import di.uiModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin


fun main() {
    startKoin {
        modules(
            appModule, uiModule
        )
    }
    val clothesSuggesterUI: ClothesSuggesterUI = getKoin().get()
    clothesSuggesterUI.start()
}