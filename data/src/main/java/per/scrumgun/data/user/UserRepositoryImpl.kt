package per.scrumgun.data.user

import android.content.Intent
import per.scrumgun.domain.chat.ChatRepository
import per.scrumgun.domain.friend.FriendRepository
import per.scrumgun.domain.message.MessageRepository
import per.scrumgun.domain.model.User
import per.scrumgun.domain.user.UserRepository

class UserRepositoryImpl(
    private val networkUserDataSource: NetworkUserDataSource,
    private val localUserDataSource: LocalUserDataSource,
    private val messageRepository: MessageRepository,
    private val friendRepository: FriendRepository,
    private val chatRepository: ChatRepository,
) : UserRepository {
    override suspend fun setUser(data: Intent?) {
        val user = networkUserDataSource.setUser(data)
        localUserDataSource.setUser(user)
    }

    override suspend fun getUser(): User {
        return localUserDataSource.getUser()
    }

    override suspend fun logout() {
        localUserDataSource.logout()
        messageRepository.clearAll()
        friendRepository.clearAll()
        chatRepository.clearAll()
    }
}
