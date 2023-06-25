package per.scrumgun.data.user

import android.content.Intent
import per.scrumgun.domain.model.User

interface NetworkUserDataSource {
    suspend fun setUser(data: Intent?): User
}
