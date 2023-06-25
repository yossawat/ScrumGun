package per.scrumgun.data.user

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import per.scrumgun.domain.model.User
import per.scrumgun.domain.model.exception.GetUserException
import per.scrumgun.domain.model.mapper.NetworkUserToUserMapper

class NetworkUserDataSourceImpl : NetworkUserDataSource {
    override suspend fun setUser(data: Intent?): User {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
        return NetworkUserToUserMapper.map(account ?: throw GetUserException())
    }
}
