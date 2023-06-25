package per.scrumgun.domain.user

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
class LogoutUserUseCaseTest : BaseUseCaseTest() {
    private val userRepository: UserRepository = mockk()
    private lateinit var underTest: LogoutUserUseCase

    override fun setUpTest() {
        underTest = LogoutUserUseCase(
            userRepository,
        )
    }

    @Test
    fun testLogout() = runTest {
        coEvery {
            userRepository.logout()
        } returns Unit

        val result = underTest.invoke(Unit)

        result.assertValue {
            it.isSuccess
        }
        coVerify(exactly = 1) {
            userRepository.logout()
        }
    }
}

