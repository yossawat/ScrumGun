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
import per.scrumgun.domain.model.User
import per.scrumgun.domain.user.GetUserUseCase
import per.scrumgun.domain.user.LogoutUserUseCase

class HomeViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider,
    private val themeViewModelDelegate: ThemeViewModelDelegate
) : ViewModel(), ThemeViewModelDelegate by themeViewModelDelegate {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _userProgress = MutableLiveData<Boolean>()
    val userProgress: LiveData<Boolean>
        get() = _userProgress

    private val _getUserFailedEvent = LiveEvent<String>()
    val getUserFailedEvent: LiveData<String>
        get() = _getUserFailedEvent

    init {
        viewModelScope.launch {
            try {
                _userProgress.value = true
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getUserUseCase(Unit).successOrThrow()
                }
                _user.value = result
            } catch (e: Exception) {
                _getUserFailedEvent.value = e.message
            } finally {
                _userProgress.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                _userProgress.value = true
                withContext(coroutinesDispatcherProvider.io) {
                    logoutUserUseCase(Unit).successOrThrow()
                }
            } catch (e: Exception) {
                _getUserFailedEvent.value = e.message
            } finally {
                _userProgress.value = false
            }
        }
    }
}
