package per.scrumgun.scrum

import com.jraska.livedata.TestObserver
import com.jraska.livedata.test
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.domain.model.User
import per.scrumgun.domain.user.GetUserUseCase
import per.scrumgun.domain.user.LogoutUserUseCase
import per.scrumgun.test.base.BaseViewModelTest
import per.scrumgun.test.testdata.UserTestData
import per.scrumgun.test.utils.failResult
import per.scrumgun.test.utils.mockReturnResult
import per.scrumgun.test.utils.provideFakeCoroutinesDispatcherProvider
import per.scrumgun.test.utils.successResult

@RunWith(JUnit4::class)
class HomeViewModelTest : BaseViewModelTest() {
    private val getUserUseCase: GetUserUseCase = mockk()
    private val logoutUserUseCase: LogoutUserUseCase = mockk()
    private val themeViewModelDelegate: ThemeViewModelDelegate = ThemeViewModelDelegateImpl()

    private lateinit var underTest: HomeViewModel
    private lateinit var userObserver: TestObserver<User>
    private lateinit var getUserFailedEventObserver: TestObserver<String>

    private fun initViewModel() {
        underTest = HomeViewModel(
            getUserUseCase,
            logoutUserUseCase,
            provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher),
            themeViewModelDelegate
        )
        userObserver = underTest.user.test()
        getUserFailedEventObserver = underTest.getUserFailedEvent.test()
    }

    @Test
    fun testGetUser() {
        getUserUseCase.mockReturnResult {
            successResult(UserTestData.user)
        }

        initViewModel()

        userObserver.assertValue(UserTestData.user)
        getUserFailedEventObserver.assertNoValue()
        coVerify(exactly = 1) {
            getUserUseCase.invoke(Unit)
        }
    }

    @Test
    fun testGetUserFail() {
        getUserUseCase.mockReturnResult {
            failResult()
        }

        initViewModel()

        userObserver.assertNoValue()
        getUserFailedEventObserver.assertNoValue()
        coVerify(exactly = 1) {
            getUserUseCase.invoke(Unit)
        }
    }

    @Test
    fun testLogout() {
        logoutUserUseCase.mockReturnResult {
            successResult(Unit)
        }

        initViewModel()
        underTest.logout()

        getUserFailedEventObserver.assertNoValue()
        coVerify(exactly = 1) {
            logoutUserUseCase.invoke(Unit)
        }
    }

    @Test
    fun testLogoutFail() {
        logoutUserUseCase.mockReturnResult {
            failResult()
        }

        initViewModel()
        underTest.logout()

        getUserFailedEventObserver.assertHasValue()
        coVerify(exactly = 1) {
            logoutUserUseCase.invoke(Unit)
        }
    }
}
