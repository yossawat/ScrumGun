package per.scrumgun.scrum.di

import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import per.scrumgun.scrum.*

val scrumModule = module {
    viewModel {
        LoginViewModel(get(), get(), get(), get())
    }
    viewModel {
        HomeViewModel(get(), get(), get(), get(), get())
    }
    viewModel {
        ScrumViewModel(get(), get(), get(), get(), get(), get(), get(), get())
    }
    single { FirebaseAuth.getInstance() }
    single<ThemeViewModelDelegate> { ThemeViewModelDelegateImpl() }
}
