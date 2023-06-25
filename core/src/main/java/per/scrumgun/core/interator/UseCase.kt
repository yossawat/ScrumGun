package per.scrumgun.core.interator

import kotlinx.coroutines.CancellationException
import per.scrumgun.core.model.Result
import timber.log.Timber

abstract class UseCase<in P, R> {

    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            Result.Success(execute(parameters))
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Timber.e(e)
            Result.Failure(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}
