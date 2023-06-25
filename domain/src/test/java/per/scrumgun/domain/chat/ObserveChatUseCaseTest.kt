package per.scrumgun.domain.chat

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.core.model.Result
import per.scrumgun.test.base.BaseUseCaseTest
import per.scrumgun.test.testdata.ChatTestData
import per.scrumgun.test.utils.assertValues
import per.scrumgun.test.utils.provideFakeCoroutinesDispatcherProvider

@RunWith(JUnit4::class)
class ObserveChatUseCaseTest : BaseUseCaseTest() {
    private val chatRepository: ChatRepository = mockk()
    private lateinit var underTest: ObserveChatUseCase

    override fun setUpTest() {
        underTest = ObserveChatUseCase(
            chatRepository,
        )
    }

    @Test
    fun testObserveChat() = runTest {
        coEvery {
            chatRepository.observeChat()
        } returns flowOf(ChatTestData.all())

        val result = underTest.invoke(
            Unit,
            provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
        ).toList()

        result.assertValues(
            Result.Progress,
            Result.Success(ChatTestData.all())
        )
        verify(exactly = 1) {
            chatRepository.observeChat()
        }
    }
}
