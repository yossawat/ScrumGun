package per.scrumgun.data.friend

import kotlinx.coroutines.flow.Flow
import per.scrumgun.domain.model.Friend

interface NetworkFriendDataSource {
    fun observeFriends(): Flow<List<Friend>>
}
