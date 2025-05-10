package di

import org.koin.dsl.module
import presentation.ClothesSuggesterUI
import presentation.io.ConsoleIO
import presentation.io.ConsoleIOImpl

val uiModule = module {
    single { ClothesSuggesterUI(
        get(),
        get(),
        get()
    ) }

    single<ConsoleIO> { ConsoleIOImpl() }
}