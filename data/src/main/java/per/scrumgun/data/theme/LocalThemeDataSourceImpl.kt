package per.scrumgun.data.theme

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import per.scrumgun.core.db.model.theme.ThemeDao
import per.scrumgun.data.model.ThemeEntityToThemeMapper
import per.scrumgun.data.model.ThemeToThemeEntityMapper
import per.scrumgun.domain.model.Theme

class LocalThemeDataSourceImpl(private val themeDao: ThemeDao) : LocalThemeDataSource {
    override suspend fun setTheme(theme: Theme) {
        themeDao.insertOrUpdate(ThemeToThemeEntityMapper.map(theme))
    }

    override fun observeTheme(): Flow<Theme> {
        return themeDao.observeFindAll().map {
            ThemeEntityToThemeMapper.map(it)
        }
    }
}
