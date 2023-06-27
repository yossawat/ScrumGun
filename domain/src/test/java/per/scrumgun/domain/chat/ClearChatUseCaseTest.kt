package per.scrumgun.domain.chat

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.domain.friend.FriendRepository
import per.scrumgun.test.base.BaseUseCaseTest
import per.scrumgun.test.utils.assertValue

@RunWith(JUnit4::class)
class ClearChatUseCaseTest : BaseUseCaseTest() {
    private val chatRepository: ChatRepository = mockk()
    private val friendRepository: FriendRepository = mockk()
    private lateinit var underTest: ClearChatUseCase

    override fun setUpTest() {
        underTest = ClearChatUseCase(
            chatRepository,
            friendRepository
        )
    }

    @Test
    fun testClearChat() = runTest {
        coEvery {
            chatRepository.clearAll()
        } returns Unit
        coEvery {
            friendRepository.clearAll()
        } returns Unit

        val result = underTest.invoke(Unit)

        result.assertValue {
            it.isSuccess
        }
        coVerify(exactly = 1) {
            chatRepository.clearAll()
            friendRepository.clearAll()
        }
    }
}
