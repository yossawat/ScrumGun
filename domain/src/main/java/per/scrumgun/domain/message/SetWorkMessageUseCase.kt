package per.scrumgun.domain.message

import per.scrumgun.core.interator.UseCase

class SetWorkMessageUseCase(private val messageRepository: MessageRepository) :
    UseCase<String, Unit>() {
    override suspend fun execute(parameters: String) {
        return messageRepository.setWorkMessage(parameters)
    }
}
