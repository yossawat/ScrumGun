package per.scrumgun.domain.user

import android.content.Intent
import per.scrumgun.core.interator.UseCase

class SetUserUseCase(private val userRepository: UserRepository) : UseCase<Intent?, Unit>() {
    override suspend fun execute(parameters: Intent?) {
        userRepository.setUser(parameters)
    }
}
