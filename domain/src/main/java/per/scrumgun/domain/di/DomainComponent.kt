package per.scrumgun.domain.di

import org.koin.core.context.loadKoinModules

object DomainComponent {
    fun init() = loadKoinModules(domainModule)
}
