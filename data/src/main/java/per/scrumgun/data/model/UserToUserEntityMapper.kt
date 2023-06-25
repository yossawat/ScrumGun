package per.scrumgun.data.model

import per.scrumgun.core.db.model.user.UserEntity
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.User

object UserToUserEntityMapper : Mapper<User, UserEntity> {
    override fun map(from: User): UserEntity {
        return UserEntity(
            id = from.id,
            name = from.name,
            email = from.email
        )
    }
}
