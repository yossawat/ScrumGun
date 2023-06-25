package per.scrumgun.domain.model.mapper

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import per.scrumgun.core.mapper.Mapper
import per.scrumgun.domain.model.User

object NetworkUserToUserMapper : Mapper<GoogleSignInAccount, User> {
    override fun map(from: GoogleSignInAccount): User {
        return User(
            id = from.id ?: "",
            name = from.displayName ?: "",
            email = from.email ?: ""
        )
    }
}
