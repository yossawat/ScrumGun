package per.scrumgun.data.theme

import kotlinx.coroutines.flow.Flow
import per.scrumgun.domain.model.Theme

interface NetworkThemeDataSource {
    fun observeTheme(): Flow<Theme>
}
