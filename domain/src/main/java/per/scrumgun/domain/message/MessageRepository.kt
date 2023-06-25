package per.scrumgun.domain.message

import per.scrumgun.domain.model.Message
import per.scrumgun.domain.model.form.SendMessageForm

interface MessageRepository {
    suspend fun setWorkMessage(message: String)
    suspend fun setBlockMessage(message: String)
    suspend fun getMessage(): Message?
    suspend fun sendMessage(sendMessageForm: SendMessageForm)
    suspend fun clearAll()
}
