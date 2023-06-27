package per.scrumgun.domain.chat

import per.scrumgun.core.interator.UseCase
import per.scrumgun.domain.friend.FriendRepository

class ClearChatUseCase(
    private val chatRepository: ChatRepository,
    private val friendRepository: FriendRepository
) : UseCase<Unit, Unit>() {
    override suspend fun execute(parameters: Unit) {
        friendRepository.clearAll()
        chatRepository.clearAll()
    }
}

