package per.scrumgun.data.model

import per.scrumgun.core.db.model.theme.ThemeEntity
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.Theme

object ThemeEntityToThemeMapper : Mapper<ThemeEntity, Theme> {
    override fun map(from: ThemeEntity): Theme {
        return Theme(background = from.background, text = from.text)
    }
}
