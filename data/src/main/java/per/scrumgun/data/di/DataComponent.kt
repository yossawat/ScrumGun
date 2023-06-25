package per.scrumgun.data.di

import org.koin.core.context.loadKoinModules

object DataComponent {
    fun init() = loadKoinModules(dataModule)
}
