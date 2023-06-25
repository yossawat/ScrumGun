package per.scrumgun.core.di

import org.koin.dsl.module
import per.scrumgun.core.interator.CoroutinesDispatcherProvider

val coreModule = module {
    single { CoroutinesDispatcherProvider() }
    single { MyFirebaseDatabaseBuilder().build() }
}
