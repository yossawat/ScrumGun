package per.scrumgun.test.utils

import androidx.lifecycle.MutableLiveData
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import per.scrumgun.core.interator.MediatorUseCase
import per.scrumgun.core.interator.UseCase
import per.scrumgun.core.interator.invoke
import per.scrumgun.core.model.Result

@Suppress("NOTHING_TO_INLINE")
inline fun <reified P : Any, R : Any?> UseCase<P, R>.mockReturnResult(crossinline answer: (P?) -> Result<R>) {
    coEvery { this@mockReturnResult(any()) } coAnswers {
        val param: P? = firstArg()
        answer(param)
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun <reified P : Any, R : Any?> UseCase<P, R>.mockResult(crossinline answer: (P, MutableLiveData<Result<R>>) -> Unit) {
    coEvery { this@mockResult(any(), any(), any()) } coAnswers {
        val param: P = secondArg()
        val liveData: MutableLiveData<Result<R>> = thirdArg()
        answer(param, liveData)
    }
}

inline fun <reified P : Any, R : Any?> MediatorUseCase<P, R>.mockResult(crossinline answer: suspend (P, FlowCollector<Result<R>>) -> Unit) {
    every { this@mockResult.invoke(any(), any()) } answers {
        val param: P = firstArg()
        flow {
            answer(param, this)
        }
    }
}
