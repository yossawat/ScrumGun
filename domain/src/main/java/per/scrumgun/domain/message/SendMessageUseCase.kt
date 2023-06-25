package per.scrumgun.domain.message

import per.scrumgun.core.interator.UseCase
import per.scrumgun.core.util.TimeUtils
import per.scrumgun.domain.model.exception.GetMessageException
import per.scrumgun.domain.model.form.SendMessageForm
import per.scrumgun.domain.user.UserRepository

class SendMessageUseCase(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository
) : UseCase<Unit, Unit>() {
    override suspend fun execute(parameters: Unit) {
        val user = userRepository.getUser()
        val message = messageRepository.getMessage()
        return messageRepository.sendMessage(
            SendMessageForm(
                userId = user.id,
                userName = user.name,
                workMessage = message?.workMessage ?: throw GetMessageException(),
                blockMessage = message.blockMessage ?: throw GetMessageException(),
                sentTime = TimeUtils.getTimeStamp()
            )
        )
    }
}
