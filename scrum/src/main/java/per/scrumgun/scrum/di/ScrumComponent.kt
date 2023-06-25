package per.scrumgun.scrum.di

import org.koin.core.context.loadKoinModules

object ScrumComponent {
    fun init() = loadKoinModules(scrumModule)
}
