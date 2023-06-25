package per.scrumgun.test.utils

import kotlinx.coroutines.test.TestDispatcher
import per.scrumgun.core.interator.CoroutinesDispatcherProvider

fun provideFakeCoroutinesDispatcherProvider(dispatcher: TestDispatcher): CoroutinesDispatcherProvider {
    return CoroutinesDispatcherProvider(dispatcher, dispatcher, dispatcher)
}
