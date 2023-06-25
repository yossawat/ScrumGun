package per.scrumgun.data.message

import per.scrumgun.core.UserId
import per.scrumgun.domain.model.form.SendMessageForm

interface NetworkMessageDataSource {
    suspend fun sendFriend(userId: UserId, userName: String)
    suspend fun sendMessage(sendMessageForm: SendMessageForm)
}
