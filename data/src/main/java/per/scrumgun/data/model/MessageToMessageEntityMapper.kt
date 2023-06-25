package per.scrumgun.data.model

import per.scrumgun.core.MESSAGE_ID
import per.scrumgun.core.db.model.message.MessageEntity
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.data.model.MessageToMessageEntityMapper.Params.Type.BLOCK
import per.scrumgun.data.model.MessageToMessageEntityMapper.Params.Type.WORK

object MessageToMessageEntityMapper : Mapper<MessageToMessageEntityMapper.Params, MessageEntity> {
    override fun map(from: Params): MessageEntity {
        val messageEntity = from.messageEntity
        val message = from.message
        return when (from.case) {
            WORK -> MessageEntity(
                id = MESSAGE_ID,
                workMessage = message,
                blockMessage = messageEntity?.blockMessage
            )
            BLOCK -> MessageEntity(
                id = MESSAGE_ID,
                workMessage = messageEntity?.workMessage,
                blockMessage = message
            )
        }
    }

    data class Params(
        val messageEntity: MessageEntity?,
        val message: String,
        val case: Type
    ) {
        enum class Type {
            WORK,
            BLOCK
        }
    }
}
