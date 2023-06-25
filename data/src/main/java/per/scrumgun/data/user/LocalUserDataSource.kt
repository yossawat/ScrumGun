package per.scrumgun.data.user

import per.scrumgun.domain.model.User

interface LocalUserDataSource {
    suspend fun setUser(user: User)
    suspend fun getUser(): User
    suspend fun logout()
}
