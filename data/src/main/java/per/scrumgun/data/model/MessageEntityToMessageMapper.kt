package per.scrumgun.data.model

import per.scrumgun.core.db.model.message.MessageEntity
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.Message

object MessageEntityToMessageMapper : Mapper<MessageEntity, Message> {
    override fun map(from: MessageEntity): Message {
        return Message(
            id = from.id,
            workMessage = from.workMessage,
            blockMessage = from.blockMessage
        )
    }
}
