package per.scrumgun.data.user

import per.scrumgun.core.db.model.user.UserDao
import per.scrumgun.data.model.UserEntityToUserMapper
import per.scrumgun.data.model.UserToUserEntityMapper
import per.scrumgun.domain.model.User
import per.scrumgun.domain.model.exception.GetUserException

class LocalUserDataSourceImpl(private val userDao: UserDao) : LocalUserDataSource {
    override suspend fun setUser(user: User) {
        userDao.insert(UserToUserEntityMapper.map(user))
    }

    override suspend fun getUser(): User {
        return UserEntityToUserMapper.map(userDao.findAll() ?: throw GetUserException())
    }

    override suspend fun logout() {
        userDao.clearAll()
    }
}
