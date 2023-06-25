package per.scrumgun.domain.model.mapper

import per.scrumgun.core.DefaultTheme
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.core.model.theme.NetworkTheme
import per.scrumgun.domain.model.Theme

object NetworkThemeToThemeMapper : Mapper<NetworkTheme, Theme> {
    override fun map(from: NetworkTheme): Theme {
        return Theme(
            background = from.background ?: DefaultTheme.BACKGROUND,
            text = from.text ?: DefaultTheme.TEXT
        )
    }
}
