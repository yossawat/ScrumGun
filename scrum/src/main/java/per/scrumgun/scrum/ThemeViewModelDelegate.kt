package per.scrumgun.scrum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import per.scrumgun.domain.model.Theme

interface ThemeViewModelDelegate {
    val theme: LiveData<Theme>
    fun setTheme(theme: Theme)
}

class ThemeViewModelDelegateImpl : ThemeViewModelDelegate {
    private val _theme = MutableLiveData<Theme>()
    override val theme: LiveData<Theme>
        get() = _theme

    override fun setTheme(theme: Theme) {
        _theme.value = theme
    }
}
