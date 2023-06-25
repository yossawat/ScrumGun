package per.scrumgun.domain.model

import per.scrumgun.core.ChatId
import per.scrumgun.core.UserId
import java.util.*

data class Chat(
    val id: ChatId,
    val userId: UserId,
    val workMessage: String,
    val blockMessage: String,
    val sentTime: Date,
)
