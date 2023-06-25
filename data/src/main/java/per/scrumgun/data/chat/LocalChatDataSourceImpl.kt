package per.scrumgun.data.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import per.scrumgun.core.db.model.chat.ChatDao
import per.scrumgun.data.model.ChatEntityToChatMapper
import per.scrumgun.data.model.ChatToChatEntityMapper
import per.scrumgun.domain.model.Chat

class LocalChatDataSourceImpl(private val chatDao: ChatDao) : LocalChatDataSource {
    override suspend fun setChats(chats: List<Chat>) {
        chatDao.insertOrUpdate(chats.map { ChatToChatEntityMapper.map(it) })
    }

    override fun observeChats(): Flow<List<Chat>> {
        return chatDao.observeFindAll().map {
            it.map(ChatEntityToChatMapper::map)
        }
    }

    override suspend fun clearAll() {
        chatDao.clearAll()
    }
}
