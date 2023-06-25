package per.scrumgun.domain.user

import android.content.Intent
import per.scrumgun.domain.model.User

interface UserRepository {
    suspend fun setUser(data: Intent?)
    suspend fun getUser(): User
    suspend fun logout()
}
