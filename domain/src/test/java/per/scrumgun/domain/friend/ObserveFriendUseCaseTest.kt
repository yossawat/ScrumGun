package per.scrumgun.domain.friend

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
import per.scrumgun.test.testdata.FriendTestData
import per.scrumgun.test.utils.assertValues
import per.scrumgun.test.utils.provideFakeCoroutinesDispatcherProvider

@RunWith(JUnit4::class)
class ObserveFriendUseCaseTest : BaseUseCaseTest() {
    private val friendRepository: FriendRepository = mockk()
    private lateinit var underTest: ObserveFriendUseCase

    override fun setUpTest() {
        underTest = ObserveFriendUseCase(
            friendRepository,
        )
    }

    @Test
    fun testObserveFriends() = runTest {
        coEvery {
            friendRepository.observeFriends()
        } returns flowOf(FriendTestData.all())

        val result = underTest.invoke(
            Unit,
            provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
        ).toList()

        result.assertValues(
            Result.Progress,
            Result.Success(FriendTestData.all())
        )
        verify(exactly = 1) {
            friendRepository.observeFriends()
        }
    }
}
