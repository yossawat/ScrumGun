package per.scrumgun.data.model

import per.scrumgun.core.db.model.chat.ChatEntity
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.Chat

object ChatToChatEntityMapper : Mapper<Chat, ChatEntity> {
    override fun map(from: Chat): ChatEntity {
        return ChatEntity(
            id = from.id,
            userId = from.userId,
            workMessage = from.workMessage,
            blockMessage = from.blockMessage,
            sentTime = from.sentTime
        )
    }
}
