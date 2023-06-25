package per.scrumgun.data.chat

import kotlinx.coroutines.flow.Flow
import per.scrumgun.domain.model.Chat

interface NetworkChatDataSource {
    fun observeChat(): Flow<List<Chat>>
}
