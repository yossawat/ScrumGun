package per.scrumgun.domain.friend

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import per.scrumgun.core.interator.MediatorUseCase
import per.scrumgun.core.model.Result
import per.scrumgun.domain.model.Friend

class ObserveFriendUseCase(private val friendRepository: FriendRepository) :
    MediatorUseCase<Unit, List<Friend>>() {
    override fun execute(parameters: Unit): Flow<Result<List<Friend>>> {
        return friendRepository.observeFriends().map {
            Result.Success(it)
        }
    }
}
