package per.scrumgun.domain.message

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.test.base.BaseUseCaseTest
import per.scrumgun.test.utils.assertValue

@RunWith(JUnit4::class)
class SetBlockMessageUseCaseTest : BaseUseCaseTest() {
    private val messageRepository: MessageRepository = mockk()
    private lateinit var underTest: SetBlockMessageUseCase

    override fun setUpTest() {
        underTest = SetBlockMessageUseCase(
            messageRepository,
        )
    }

    @Test
    fun testSetBlockMessage() = runTest {
        coEvery {
            messageRepository.setBlockMessage(any())
        } returns Unit

        val result = underTest.invoke("blockMessage")

        result.assertValue {
            it.isSuccess
        }
        coVerify(exactly = 1) {
            messageRepository.setBlockMessage("blockMessage")
        }
    }
}
