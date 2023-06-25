package per.scrumgun.scrum

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import per.scrumgun.core.DefaultTheme
import per.scrumgun.core.interator.CoroutinesDispatcherProvider
import per.scrumgun.core.model.successOr
import per.scrumgun.core.model.successOrThrow
import per.scrumgun.core.util.combineLatest
import per.scrumgun.domain.chat.ObserveChatUseCase
import per.scrumgun.domain.friend.ObserveFriendUseCase
import per.scrumgun.domain.message.GetMessageUseCase
import per.scrumgun.domain.message.SendMessageUseCase
import per.scrumgun.domain.message.SetBlockMessageUseCase
import per.scrumgun.domain.message.SetWorkMessageUseCase
import per.scrumgun.domain.model.Message
import per.scrumgun.scrum.mapper.ChatsToUiChatsMapper

class ScrumViewModel(
    private val setWorkMessageUseCase: SetWorkMessageUseCase,
    private val setBlockMessageUseCase: SetBlockMessageUseCase,
    private val getMessageUseCase: GetMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    observeFriendUseCase: ObserveFriendUseCase,
    observeChatUseCase: ObserveChatUseCase,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider,
    private val themeViewModelDelegate: ThemeViewModelDelegate
) : ViewModel(), ThemeViewModelDelegate by themeViewModelDelegate {
    private val friendsResult =
        observeFriendUseCase.invoke(Unit, coroutinesDispatcherProvider.io).asLiveData()

    private val chatsResult =
        observeChatUseCase.invoke(Unit, coroutinesDispatcherProvider.io).asLiveData()

    val observeChats = combineLatest(friendsResult, chatsResult, theme)
    { friendsResult, chatsResult, theme ->
        ChatsToUiChatsMapper.map(
            ChatsToUiChatsMapper.Params(
                friends = friendsResult?.successOr(emptyList()) ?: listOf(),
                chats = chatsResult?.successOr(emptyList()) ?: listOf(),
                textColor = theme?.text ?: DefaultTheme.TEXT
            )
        )
    }

    private val _scrumViewModelProgress = MutableLiveData<Boolean>()
    val scrumViewModelProgress: LiveData<Boolean>
        get() = _scrumViewModelProgress

    private val _scrumViewModelFailedEvent = LiveEvent<String>()
    val scrumViewModelFailedEvent: LiveData<String>
        get() = _scrumViewModelFailedEvent

    private val _isWorkMessageEmpty = MutableLiveData<Boolean>()
    val isWorkMessageEmpty: LiveData<Boolean>
        get() = _isWorkMessageEmpty

    private val _isBlockMessageEmpty = MutableLiveData<Boolean>()
    val isBlockMessageEmpty: LiveData<Boolean>
        get() = _isBlockMessageEmpty

    private val _isMessageEmpty = MutableLiveData<Boolean>()
    val isMessageEmpty: LiveData<Boolean>
        get() = _isMessageEmpty

    init {
        getMessage()
    }

    fun setWorkMessage(message: String) {
        viewModelScope.launch {
            try {
                _scrumViewModelProgress.value = true
                withContext(coroutinesDispatcherProvider.io) {
                    setWorkMessageUseCase(message).successOrThrow()
                }
                getMessage()
            } catch (e: Exception) {
                _scrumViewModelFailedEvent.value = e.message
            } finally {
                _scrumViewModelProgress.value = false
            }
        }
    }

    fun setBlockMessage(message: String) {
        viewModelScope.launch {
            try {
                _scrumViewModelProgress.value = true
                withContext(coroutinesDispatcherProvider.io) {
                    setBlockMessageUseCase(message).successOrThrow()
                }
                getMessage()
            } catch (e: Exception) {
                _scrumViewModelFailedEvent.value = e.message
            } finally {
                _scrumViewModelProgress.value = false
            }
        }
    }

    private fun getMessage() {
        viewModelScope.launch {
            try {
                _scrumViewModelProgress.value = true
                val result = withContext(coroutinesDispatcherProvider.io) {
                    getMessageUseCase(Unit).successOrThrow()
                }
                setIsMessageResult(result)
            } catch (e: Exception) {
                _scrumViewModelFailedEvent.value = e.message
            } finally {
                _scrumViewModelProgress.value = false
            }
        }
    }

    private fun setIsMessageResult(result: Message?) {
        _isWorkMessageEmpty.value = result?.workMessage.isNullOrEmpty()
        _isBlockMessageEmpty.value = result?.blockMessage.isNullOrEmpty()
        _isMessageEmpty.value =
            result?.workMessage?.isNotEmpty() == true && result.blockMessage?.isNotEmpty() == true
    }

    fun sendMessage() {
        viewModelScope.launch {
            try {
                _scrumViewModelProgress.value = true
                withContext(coroutinesDispatcherProvider.io) {
                    sendMessageUseCase(Unit).successOrThrow()
                }
                getMessage()
            } catch (e: Exception) {
                _scrumViewModelFailedEvent.value = e.message
            } finally {
                _scrumViewModelProgress.value = false
            }
        }
    }
}
