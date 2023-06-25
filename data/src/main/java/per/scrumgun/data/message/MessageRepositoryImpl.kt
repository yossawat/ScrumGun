package per.scrumgun.data.message

import per.scrumgun.domain.message.MessageRepository
import per.scrumgun.domain.model.Message
import per.scrumgun.domain.model.form.SendMessageForm

class MessageRepositoryImpl(
    private val localMessageDataSource: LocalMessageDataSource,
    private val networkMessageDataSource: NetworkMessageDataSource,
) : MessageRepository {
    override suspend fun setWorkMessage(message: String) {
        localMessageDataSource.setWorkMessage(message = message)
    }

    override suspend fun setBlockMessage(message: String) {
        localMessageDataSource.setBlockMessage(message = message)
    }

    override suspend fun getMessage(): Message? {
        return localMessageDataSource.getMessage()
    }

    override suspend fun sendMessage(sendMessageForm: SendMessageForm) {
        networkMessageDataSource.sendFriend(
            userId = sendMessageForm.userId,
            userName = sendMessageForm.userName
        )
        networkMessageDataSource.sendMessage(sendMessageForm = sendMessageForm)
        localMessageDataSource.clearAll()
    }

    override suspend fun clearAll() {
        localMessageDataSource.clearAll()
    }
}
