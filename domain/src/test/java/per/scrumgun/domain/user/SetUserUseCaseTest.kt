package per.scrumgun.domain.user

import android.content.Intent
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
class SetUserUseCaseTest : BaseUseCaseTest() {
    private val userRepository: UserRepository = mockk()
    private lateinit var underTest: SetUserUseCase

    override fun setUpTest() {
        underTest = SetUserUseCase(
            userRepository,
        )
    }

    @Test
    fun testSetUser() = runTest {
        val data: Intent = mockk()
        coEvery {
            userRepository.setUser(data)
        } returns Unit

        val result = underTest.invoke(data)

        result.assertValue {
            it.isSuccess
        }
        coVerify(exactly = 1) {
            userRepository.setUser(data)
        }
    }
}

