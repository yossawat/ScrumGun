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
class SetWorkMessageUseCaseTest : BaseUseCaseTest() {
    private val messageRepository: MessageRepository = mockk()
    private lateinit var underTest: SetWorkMessageUseCase

    override fun setUpTest() {
        underTest = SetWorkMessageUseCase(
            messageRepository,
        )
    }

    @Test
    fun testSetWorkMessage() = runTest {
        coEvery {
            messageRepository.setWorkMessage(any())
        } returns Unit

        val result = underTest.invoke("workMessage")

        result.assertValue {
            it.isSuccess
        }
        coVerify(exactly = 1) {
            messageRepository.setWorkMessage("workMessage")
        }
    }
}
