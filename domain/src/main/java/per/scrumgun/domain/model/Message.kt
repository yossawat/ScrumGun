package per.scrumgun.domain.model

import per.scrumgun.core.MessageId

data class Message(
    val id: MessageId,
    val workMessage: String?,
    val blockMessage: String?,
)
