package per.scrumgun.domain.friend

import kotlinx.coroutines.flow.Flow
import per.scrumgun.domain.model.Friend

interface FriendRepository {
    fun observeFriends(): Flow<List<Friend>>
    suspend fun clearAll()
}
