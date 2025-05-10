package di

import org.koin.dsl.module
import presentation.ClothesSuggesterUI

val uiModule = module {
    single { ClothesSuggesterUI(
        get(),
        get(),
        get()
    ) }
}