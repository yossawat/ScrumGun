package per.scrumgun.test.testdata

import per.scrumgun.core.DefaultTheme
import per.scrumgun.domain.model.Theme

object ThemeTestData {
    val defaultTheme = Theme(
        background = DefaultTheme.BACKGROUND,
        text = DefaultTheme.TEXT
    )
}
