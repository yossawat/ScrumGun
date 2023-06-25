package per.scrumgun.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import per.scrumgun.MainViewModel

val appModule = module {
    viewModel {
        MainViewModel(get(), get(), get())
    }
}
