package per.scrumgun.data.model.form

import per.scrumgun.core.UserId

data class SendMessageNetworkForm(
    val userId: UserId,
    val workMessage: String,
    val blockMessage: String,
    val sentTime: String,
)
