package per.scrumgun.data.message

import per.scrumgun.domain.model.Message

interface LocalMessageDataSource {
    suspend fun setWorkMessage(message: String)
    suspend fun setBlockMessage(message: String)
    suspend fun getMessage(): Message?
    suspend fun clearAll()
}
