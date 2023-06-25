package per.scrumgun.data.friend

import kotlinx.coroutines.flow.Flow
import per.scrumgun.domain.model.Friend

interface LocalFriendDataSource {
    suspend fun setFriends(friend: List<Friend>)
    fun observeFriends(): Flow<List<Friend>>
    suspend fun clearAll()
}
