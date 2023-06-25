package per.scrumgun.scrum

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import per.scrumgun.core.GoogleLogin
import per.scrumgun.core.interator.CoroutinesDispatcherProvider
import per.scrumgun.core.model.successOrThrow
import per.scrumgun.domain.model.exception.GetUserException
import per.scrumgun.domain.user.GetUserUseCase
import per.scrumgun.domain.user.SetUserUseCase

class LoginViewModel(
    private val setUserUseCase: SetUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider,
    private val themeViewModelDelegate: ThemeViewModelDelegate
) : ViewModel(), ThemeViewModelDelegate by themeViewModelDelegate {
    private val _navigateToHomeFragmentEvent = MutableLiveData<Unit>()
    val navigateToHomeFragmentEvent: LiveData<Unit>
        get() = _navigateToHomeFragmentEvent

    private val _loginFailedEvent = LiveEvent<String>()
    val loginFailedEvent: LiveData<String>
        get() = _loginFailedEvent

    init {
        viewModelScope.launch {
            try {
                withContext(coroutinesDispatcherProvider.io) {
                    getUserUseCase(Unit).successOrThrow()
                }
                _navigateToHomeFragmentEvent.value = Unit
            } catch (_: GetUserException) {
                //No need
            } catch (e: Exception) {
                _loginFailedEvent.value = e.message
            }
        }
    }

    fun loginWithGoogle(requestCode: Int, data: Intent?) {
        if (requestCode == GoogleLogin.REQ_CODE) {
            viewModelScope.launch {
                try {
                    withContext(coroutinesDispatcherProvider.io) {
                        setUserUseCase(data).successOrThrow()
                    }
                    _navigateToHomeFragmentEvent.value = Unit
                } catch (e: Exception) {
                    _loginFailedEvent.value = e.message
                }
            }
        }
    }
}
