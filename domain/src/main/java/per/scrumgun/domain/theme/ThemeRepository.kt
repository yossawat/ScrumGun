package per.scrumgun.domain.theme

import kotlinx.coroutines.flow.Flow
import per.scrumgun.domain.model.Theme

interface ThemeRepository {
    fun observeTheme(): Flow<Theme>
}
