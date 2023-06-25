package per.scrumgun.data.model

import per.scrumgun.core.db.model.friend.FriendEntity
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.Friend

object FriendToFriendEntityMapper : Mapper<Friend, FriendEntity> {
    override fun map(from: Friend): FriendEntity {
        return FriendEntity(id = from.id, name = from.name)
    }
}
