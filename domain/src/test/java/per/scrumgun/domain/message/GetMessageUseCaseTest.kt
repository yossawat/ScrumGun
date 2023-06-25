package per.scrumgun.domain.message

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.test.base.BaseUseCaseTest
import per.scrumgun.test.testdata.MessageTestData
import per.scrumgun.test.utils.assertValue

@RunWith(JUnit4::class)
class GetMessageUseCaseTest : BaseUseCaseTest() {
    private val messageRepository: MessageRepository = mockk()
    private lateinit var underTest: GetMessageUseCase

    override fun setUpTest() {
        underTest = GetMessageUseCase(
            messageRepository,
        )
    }

    @Test
    fun testGetMessage() = runTest {
        coEvery {
            messageRepository.getMessage()
        } returns MessageTestData.messageNotEmpty

        val result = underTest.invoke(Unit)

        result.assertValue {
            it.isSuccess
        }
        coVerify(exactly = 1) {
            messageRepository.getMessage()
        }
    }
}
