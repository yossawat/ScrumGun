package per.scrumgun.scrum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import per.scrumgun.core.interator.CoroutinesDispatcherProvider
import per.scrumgun.core.model.successOrThrow
import per.scrumgun.domain.chat.ClearChatUseCase
import per.scrumgun.domain.model.User
import per.scrumgun.domain.user.GetUserUseCase
import per.scrumgun.domain.user.LogoutUserUseCase

class HomeViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val clearChatUseCase: ClearChatUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider,
    private val themeViewModelDelegate: ThemeViewModelDelegate
) : ViewModel(), ThemeViewModelDelegate by themeViewModelDelegate {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _homeProgress = MutableLiveData<Boolean>()
    val homeProgress: LiveData<Boolean>
        get() = _homeProgress

    private val _homeFailedEvent = LiveEvent<String>()
    val homeFailedEvent: LiveData<String>
        get() = _homeFailedEvent

    init {
        viewModelScope.launch {
            try {
                _homeProgress.value = true
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getUserUseCase(Unit).successOrThrow()
                }
                _user.value = result
            } catch (e: Exception) {
                _homeFailedEvent.value = e.message
            } finally {
                _homeProgress.value = false
            }
        }
        clearChat()
    }

    fun logout() {
        viewModelScope.launch {
            try {
                _homeProgress.value = true
                withContext(coroutinesDispatcherProvider.io) {
                    logoutUserUseCase(Unit).successOrThrow()
                }
            } catch (e: Exception) {
                _homeFailedEvent.value = e.message
            } finally {
                _homeProgress.value = false
            }
        }
    }

    private fun clearChat() {
        viewModelScope.launch {
            try {
                _homeProgress.value = true
                withContext(coroutinesDispatcherProvider.io) {
                    clearChatUseCase(Unit).successOrThrow()
                }
            } catch (e: Exception) {
                _homeFailedEvent.value = e.message
            } finally {
                _homeProgress.value = false
            }
        }
    }
}
