package per.scrumgun.domain.model.form

import per.scrumgun.core.UserId

data class SendMessageForm(
    val userId: UserId,
    val userName: String,
    val workMessage: String,
    val blockMessage: String,
    val sentTime: String,
)
