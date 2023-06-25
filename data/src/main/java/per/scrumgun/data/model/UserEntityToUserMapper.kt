package per.scrumgun.data.model

import per.scrumgun.core.db.model.user.UserEntity
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.User

object UserEntityToUserMapper : Mapper<UserEntity, User> {
    override fun map(from: UserEntity): User {
        return User(
            id = from.id,
            name = from.name,
            email = from.email
        )
    }
}
