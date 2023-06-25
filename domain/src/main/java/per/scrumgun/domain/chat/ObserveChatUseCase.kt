package per.scrumgun.domain.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import per.scrumgun.core.interator.MediatorUseCase
import per.scrumgun.core.model.Result
import per.scrumgun.domain.model.Chat

class ObserveChatUseCase(private val chatRepository: ChatRepository) :
    MediatorUseCase<Unit, List<Chat>>() {
    override fun execute(parameters: Unit): Flow<Result<List<Chat>>> {
        return chatRepository.observeChat().map {
            Result.Success(it)
        }
    }
}
