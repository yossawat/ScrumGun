package per.scrumgun.data.friend

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import per.scrumgun.core.db.model.friend.FriendDao
import per.scrumgun.data.model.FriendEntityToFriendMapper
import per.scrumgun.data.model.FriendToFriendEntityMapper
import per.scrumgun.domain.model.Friend

class LocalFriendDataSourceImpl(private val friendDao: FriendDao) : LocalFriendDataSource {
    override suspend fun setFriends(friend: List<Friend>) {
        friendDao.insertOrUpdate(friend.map { FriendToFriendEntityMapper.map(it) })
    }

    override fun observeFriends(): Flow<List<Friend>> {
        return friendDao.observeFindAll().map {
            it.map(FriendEntityToFriendMapper::map)
        }
    }

    override suspend fun clearAll() {
        friendDao.clearAll()
    }
}
