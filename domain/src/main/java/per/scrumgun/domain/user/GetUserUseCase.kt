package per.scrumgun.domain.user

import per.scrumgun.core.interator.UseCase
import per.scrumgun.domain.model.User

class GetUserUseCase(private val userRepository: UserRepository) :
    UseCase<Unit, User>() {
    override suspend fun execute(parameters: Unit): User {
        return userRepository.getUser()
    }
}
