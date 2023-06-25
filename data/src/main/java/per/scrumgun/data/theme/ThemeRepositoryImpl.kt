package per.scrumgun.data.theme

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import per.scrumgun.domain.model.Theme
import per.scrumgun.domain.theme.ThemeRepository

class ThemeRepositoryImpl(
    private val networkThemeDataSource: NetworkThemeDataSource,
    private val localThemeDataSource: LocalThemeDataSource
) : ThemeRepository {
    override fun observeTheme(): Flow<Theme> {
        return networkThemeDataSource.observeTheme().flatMapLatest {
            localThemeDataSource.setTheme(it)
            localThemeDataSource.observeTheme()
        }
    }
}
