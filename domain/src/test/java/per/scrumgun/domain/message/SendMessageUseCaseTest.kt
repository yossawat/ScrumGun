package per.scrumgun.domain.message

import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.core.util.TimeUtils
import per.scrumgun.domain.model.form.SendMessageForm
import per.scrumgun.domain.user.UserRepository
import per.scrumgun.test.base.BaseUseCaseTest
import per.scrumgun.test.testdata.MessageTestData
import per.scrumgun.test.testdata.UserTestData
import per.scrumgun.test.utils.assertValue

@RunWith(JUnit4::class)
class SendMessageUseCaseTest : BaseUseCaseTest() {
    private val messageRepository: MessageRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var underTest: SendMessageUseCase

    override fun setUpTest() {
        underTest = SendMessageUseCase(
            messageRepository,
            userRepository
        )
    }

    @Test
    fun testSendMessage() = runTest {
        coEvery {
            userRepository.getUser()
        } returns UserTestData.user
        coEvery {
            messageRepository.getMessage()
        } returns MessageTestData.messageNotEmpty
        coEvery {
            messageRepository.sendMessage(any())
        } returns Unit

        val result = underTest.invoke(Unit)

        result.assertValue {
            it.isSuccess
        }
        coVerifyOrder {
            userRepository.getUser()
            messageRepository.getMessage()
            messageRepository.sendMessage(
                SendMessageForm(
                    userId = "101",
                    userName = "name",
                    workMessage = "workMessage",
                    blockMessage = "blockMessage",
                    sentTime = TimeUtils.getTimeStamp()
                )
            )
        }
    }
}
