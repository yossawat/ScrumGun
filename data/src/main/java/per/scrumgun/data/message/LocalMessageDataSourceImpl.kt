package per.scrumgun.data.message

import per.scrumgun.core.db.model.message.MessageDao
import per.scrumgun.data.model.MessageEntityToMessageMapper
import per.scrumgun.data.model.MessageToMessageEntityMapper
import per.scrumgun.data.model.MessageToMessageEntityMapper.Params.Type
import per.scrumgun.domain.model.Message

class LocalMessageDataSourceImpl(private val messageDao: MessageDao) : LocalMessageDataSource {
    override suspend fun setWorkMessage(message: String) {
        setMessage(message = message, case = Type.WORK)
    }

    override suspend fun setBlockMessage(message: String) {
        setMessage(message = message, case = Type.BLOCK)
    }

    override suspend fun getMessage(): Message? {
        return messageDao.findAll()?.let { MessageEntityToMessageMapper.map(it) }
    }

    override suspend fun clearAll() {
        messageDao.clearAll()
    }

    private suspend fun setMessage(message: String, case: Type) {
        val messageEntity = messageDao.findAll()

        messageDao.insertOrUpdate(
            MessageToMessageEntityMapper.map(
                MessageToMessageEntityMapper.Params(
                    messageEntity = messageEntity,
                    message = message,
                    case = case
                )
            )
        )
    }
}
