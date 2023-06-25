package per.scrumgun.data.model

import per.scrumgun.core.db.model.friend.FriendEntity
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.Friend

object FriendEntityToFriendMapper : Mapper<FriendEntity, Friend> {
    override fun map(from: FriendEntity): Friend {
        return Friend(id = from.id, name = from.name)
    }
}
