package per.scrumgun.data.theme

import kotlinx.coroutines.flow.Flow
import per.scrumgun.domain.model.Theme

interface LocalThemeDataSource {
    suspend fun setTheme(theme: Theme)
    fun observeTheme(): Flow<Theme>
}
