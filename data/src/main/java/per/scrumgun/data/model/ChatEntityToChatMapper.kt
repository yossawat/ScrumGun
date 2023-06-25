package per.scrumgun.data.model

import per.scrumgun.core.db.model.chat.ChatEntity
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.Chat

object ChatEntityToChatMapper : Mapper<ChatEntity, Chat> {
    override fun map(from: ChatEntity): Chat {
        return Chat(
            id = from.id,
            userId = from.userId,
            workMessage = from.workMessage,
            blockMessage = from.blockMessage,
            sentTime = from.sentTime
        )
    }
}
