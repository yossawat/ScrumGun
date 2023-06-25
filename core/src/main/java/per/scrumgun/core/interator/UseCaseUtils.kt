package per.scrumgun.core.interator

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withContext
import per.scrumgun.core.model.Result
import kotlin.coroutines.CoroutineContext

suspend operator fun <R> UseCase<Unit, R>.invoke(): Result<R> = this(Unit)
suspend operator fun <P, R> UseCase<P, R>.invoke(
    parameters: P,
    liveData: MutableLiveData<Result<R>>,
    coroutineContext: CoroutineContext
) {
    liveData.value = Result.Progress
    liveData.value = withContext(coroutineContext) { invoke(parameters) }
}

suspend operator fun <R> UseCase<Unit, R>.invoke(
    liveData: MutableLiveData<Result<R>>,
    coroutineContext: CoroutineContext
) {
    invoke(Unit, liveData, coroutineContext)
}
