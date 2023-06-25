package per.scrumgun.domain.user

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.test.base.BaseUseCaseTest
import per.scrumgun.test.testdata.UserTestData
import per.scrumgun.test.utils.assertValue

@RunWith(JUnit4::class)
class GetUserUseCaseTest : BaseUseCaseTest() {
    private val userRepository: UserRepository = mockk()
    private lateinit var underTest: GetUserUseCase

    override fun setUpTest() {
        underTest = GetUserUseCase(
            userRepository,
        )
    }

    @Test
    fun testGetUser() = runTest {
        coEvery {
            userRepository.getUser()
        } returns UserTestData.user

        val result = underTest.invoke(Unit)

        result.assertValue {
            it.isSuccess
        }
        coVerify(exactly = 1) {
            userRepository.getUser()
        }
    }
}
