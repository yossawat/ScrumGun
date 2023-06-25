package per.scrumgun.domain.model.mapper

import per.scrumgun.core.ChatId
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.core.model.chat.NetworkChat
import per.scrumgun.core.util.DateFormatUtils
import per.scrumgun.domain.model.Chat
import java.util.*

object NetworkChatsToChatsMapper : Mapper<NetworkChatsToChatsMapper.Params, Chat> {
    override fun map(from: Params): Chat {
        val chat = from.chat
        return Chat(
            id = from.id,
            userId = chat.userId ?: "",
            workMessage = chat.workMessage ?: "",
            blockMessage = chat.blockMessage ?: "",
            sentTime = chat.sentTime?.let {
                DateFormatUtils.toDateFormatMonthYearTimeSecondsWithHyphen(it)
            } ?: Date()
        )
    }

    data class Params(
        val id: ChatId,
        val chat: NetworkChat,
    )
}
