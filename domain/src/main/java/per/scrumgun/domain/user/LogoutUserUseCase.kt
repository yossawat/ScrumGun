package per.scrumgun.domain.user

import per.scrumgun.core.interator.UseCase

class LogoutUserUseCase(private val userRepository: UserRepository) : UseCase<Unit, Unit>() {
    override suspend fun execute(parameters: Unit) {
        return userRepository.logout()
    }
}
