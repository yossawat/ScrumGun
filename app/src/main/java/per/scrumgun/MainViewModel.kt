package per.scrumgun

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import per.scrumgun.core.UNKNOWN_ERROR
import per.scrumgun.core.interator.CoroutinesDispatcherProvider
import per.scrumgun.core.model.Result
import per.scrumgun.core.model.successOr
import per.scrumgun.domain.theme.ObserveThemeUseCase
import per.scrumgun.scrum.ThemeViewModelDelegate

class MainViewModel(
    private val observeThemeUseCase: ObserveThemeUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider,
    themeViewModelDelegate: ThemeViewModelDelegate,
) : ViewModel(), ThemeViewModelDelegate by themeViewModelDelegate {
    private val _getThemeFailedEvent = LiveEvent<String>()
    val getThemeFailedEvent: LiveData<String>
        get() = _getThemeFailedEvent

    init {
        viewModelScope.launch {
            try {
                observeThemeUseCase.invoke(Unit, coroutinesDispatcherProvider.io)
                    .collectLatest { themeResult ->
                        if (themeResult !is Result.Success) return@collectLatest
                        themeResult.successOr(null)?.let { setTheme(it) }
                    }
            } catch (e: Exception) {
                _getThemeFailedEvent.value = e.message ?: UNKNOWN_ERROR
            }
        }
    }
}
