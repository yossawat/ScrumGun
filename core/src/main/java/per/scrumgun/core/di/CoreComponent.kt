package per.scrumgun.core.di

import org.koin.core.context.loadKoinModules

object CoreComponent {
    fun init() = loadKoinModules(listOf(coreModule, databaseModule))
}
