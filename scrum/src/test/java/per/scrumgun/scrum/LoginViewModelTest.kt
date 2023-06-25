package per.scrumgun.scrum

import android.content.Intent
import com.jraska.livedata.TestObserver
import com.jraska.livedata.test
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.core.GoogleLogin
import per.scrumgun.domain.user.GetUserUseCase
import per.scrumgun.domain.user.SetUserUseCase
import per.scrumgun.test.base.BaseViewModelTest
import per.scrumgun.test.testdata.UserTestData
import per.scrumgun.test.utils.failResult
import per.scrumgun.test.utils.mockReturnResult
import per.scrumgun.test.utils.provideFakeCoroutinesDispatcherProvider
import per.scrumgun.test.utils.successResult

@RunWith(JUnit4::class)
class LoginViewModelTest : BaseViewModelTest() {
    private val setUserUseCase: SetUserUseCase = mockk()
    private val getUserUseCase: GetUserUseCase = mockk()
    private val themeViewModelDelegate: ThemeViewModelDelegate = ThemeViewModelDelegateImpl()

    private lateinit var underTest: LoginViewModel
    private lateinit var navigateToHomeFragmentEventObserver: TestObserver<Unit>
    private lateinit var loginFailedEventObserver: TestObserver<String>

    private fun initViewModel() {
        underTest = LoginViewModel(
            setUserUseCase,
            getUserUseCase,
            provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher),
            themeViewModelDelegate
        )
        navigateToHomeFragmentEventObserver = underTest.navigateToHomeFragmentEvent.test()
        loginFailedEventObserver = underTest.loginFailedEvent.test()
    }

    @Test
    fun testGetUser() {
        getUserUseCase.mockReturnResult {
            successResult(UserTestData.user)
        }

        initViewModel()

        navigateToHomeFragmentEventObserver.assertValue(Unit)
        loginFailedEventObserver.assertNoValue()
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

        navigateToHomeFragmentEventObserver.assertNoValue()
        loginFailedEventObserver.assertNoValue()
        coVerify(exactly = 1) {
            getUserUseCase.invoke(Unit)
        }
    }

    @Test
    fun testLoginWithGoogle() {
        val data: Intent = mockk()
        setUserUseCase.mockReturnResult {
            successResult(Unit)
        }

        initViewModel()
        underTest.loginWithGoogle(GoogleLogin.REQ_CODE, data)

        navigateToHomeFragmentEventObserver.assertValue(Unit)
        loginFailedEventObserver.assertNoValue()
        coVerify(exactly = 1) {
            setUserUseCase.invoke(data)
        }
    }

    @Test
    fun testLoginWithGoogleREQCodeIsWrong() {
        val data: Intent = mockk()
        setUserUseCase.mockReturnResult {
            failResult()
        }

        initViewModel()
        underTest.loginWithGoogle(1, data)

        navigateToHomeFragmentEventObserver.assertNoValue()
        loginFailedEventObserver.assertNoValue()
        coVerify(exactly = 0) {
            setUserUseCase.invoke(data)
        }
    }

    @Test
    fun testLoginWithGoogleFail() {
        val data: Intent = mockk()
        setUserUseCase.mockReturnResult {
            failResult()
        }

        initViewModel()
        underTest.loginWithGoogle(GoogleLogin.REQ_CODE, data)

        navigateToHomeFragmentEventObserver.assertNoValue()
        loginFailedEventObserver.assertHasValue()
        coVerify(exactly = 1) {
            setUserUseCase.invoke(data)
        }
    }
}
