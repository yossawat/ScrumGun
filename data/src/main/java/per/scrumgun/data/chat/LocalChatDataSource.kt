package per.scrumgun.data.chat

import kotlinx.coroutines.flow.Flow
import per.scrumgun.domain.model.Chat

interface LocalChatDataSource {
    suspend fun setChats(chats: List<Chat>)
    fun observeChats(): Flow<List<Chat>>
    suspend fun clearAll()
}
