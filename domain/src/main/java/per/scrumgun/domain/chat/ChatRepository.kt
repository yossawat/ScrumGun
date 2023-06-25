package per.scrumgun.domain.chat

import kotlinx.coroutines.flow.Flow
import per.scrumgun.domain.model.Chat

interface ChatRepository {
    fun observeChat(): Flow<List<Chat>>
    suspend fun clearAll()
}
