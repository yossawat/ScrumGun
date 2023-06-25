package per.scrumgun.di

import org.koin.core.context.loadKoinModules

object AppComponent {
    fun init() = loadKoinModules(appModule)
}
