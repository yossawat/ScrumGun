package per.scrumgun.domain.message

import per.scrumgun.core.interator.UseCase
import per.scrumgun.domain.model.Message

class GetMessageUseCase(private val messageRepository: MessageRepository) :
    UseCase<Unit, Message?>() {
    override suspend fun execute(parameters: Unit): Message? {
        return messageRepository.getMessage()
    }
}

