package per.scrumgun.data.model

import per.scrumgun.core.THEME_ID
import per.scrumgun.core.db.model.theme.ThemeEntity
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.Theme

object ThemeToThemeEntityMapper : Mapper<Theme, ThemeEntity> {
    override fun map(from: Theme): ThemeEntity {
        return ThemeEntity(id = THEME_ID, background = from.background, text = from.text)
    }
}
