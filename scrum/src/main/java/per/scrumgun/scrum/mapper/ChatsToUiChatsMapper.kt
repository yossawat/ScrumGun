package per.scrumgun.scrum.mapper

import per.scrumgun.core.mapper.Mapper
import per.scrumgun.core.util.DateFormatUtils
import per.scrumgun.domain.model.Chat
import per.scrumgun.domain.model.Friend
import per.scrumgun.scrum.model.UiChat
import java.util.*

object ChatsToUiChatsMapper : Mapper<ChatsToUiChatsMapper.Params, List<UiChat>> {
    override fun map(from: Params): List<UiChat> {
        val friends = from.friends
        val chats = from.chats
        return chats.map { chat ->
            UiChat(
                id = chat.id,
                workMessage = chat.workMessage,
                blockMessage = chat.blockMessage,
                senderName = friends.associateBy { it.id }[chat.userId]?.name ?: "",
                sentTime = DateFormatUtils.formatDateMonthYearSlashWithTime(
                    chat.sentTime,
                    TimeZone.getDefault()
                ),
                textColor = from.textColor
            )
        }
    }

    data class Params(
        val friends: List<Friend>,
        val chats: List<Chat>,
        val textColor: String,
    )
}
