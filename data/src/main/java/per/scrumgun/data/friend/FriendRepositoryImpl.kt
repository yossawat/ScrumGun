package per.scrumgun.data.friend

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import per.scrumgun.domain.friend.FriendRepository
import per.scrumgun.domain.model.Friend

class FriendRepositoryImpl(
    private val networkFriendDataSource: NetworkFriendDataSource,
    private val localFriendDataSource: LocalFriendDataSource
) : FriendRepository {
    override fun observeFriends(): Flow<List<Friend>> {
        return networkFriendDataSource.observeFriends().flatMapLatest {
            localFriendDataSource.setFriends(it)
            localFriendDataSource.observeFriends()
        }
    }

    override suspend fun clearAll() {
        localFriendDataSource.clearAll()
    }
}
