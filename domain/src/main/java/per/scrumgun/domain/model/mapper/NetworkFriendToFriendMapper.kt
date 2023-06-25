package per.scrumgun.domain.model.mapper

import per.scrumgun.core.UserId
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.Friend

object NetworkFriendToFriendMapper : Mapper<NetworkFriendToFriendMapper.Params, Friend> {
    override fun map(from: Params): Friend {
        return Friend(id = from.id, name = from.name)
    }

    data class Params(
        val id: UserId,
        val name: String,
    )
}
