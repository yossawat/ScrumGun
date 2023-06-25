package per.scrumgun.domain.theme

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import per.scrumgun.core.interator.MediatorUseCase
import per.scrumgun.core.model.Result
import per.scrumgun.domain.model.Theme

class ObserveThemeUseCase(private val themeRepository: ThemeRepository) :
    MediatorUseCase<Unit, Theme>() {
    override fun execute(parameters: Unit): Flow<Result<Theme>> {
        return themeRepository.observeTheme().map {
            Result.Success(it)
        }
    }
}
