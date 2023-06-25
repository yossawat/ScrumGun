package per.scrumgun.data.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import per.scrumgun.domain.chat.ChatRepository
import per.scrumgun.domain.model.Chat

class ChatRepositoryImpl(
    private val networkChatDataSource: NetworkChatDataSource,
    private val localChatDataSource: LocalChatDataSource
) : ChatRepository {
    override fun observeChat(): Flow<List<Chat>> {
        return networkChatDataSource.observeChat().flatMapLatest {
            localChatDataSource.setChats(it)
            localChatDataSource.observeChats()
        }
    }

    override suspend fun clearAll() {
        localChatDataSource.clearAll()
    }
}
