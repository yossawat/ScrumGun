package per.scrumgun

import com.jraska.livedata.TestObserver
import com.jraska.livedata.test
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.domain.model.Theme
import per.scrumgun.domain.theme.ObserveThemeUseCase
import per.scrumgun.scrum.ThemeViewModelDelegate
import per.scrumgun.scrum.ThemeViewModelDelegateImpl
import per.scrumgun.test.base.BaseViewModelTest
import per.scrumgun.test.testdata.ThemeTestData
import per.scrumgun.test.utils.mockResult
import per.scrumgun.test.utils.provideFakeCoroutinesDispatcherProvider
import per.scrumgun.test.utils.successResult

@RunWith(JUnit4::class)
class MainViewModelTest : BaseViewModelTest() {
    private val observeThemeUseCase: ObserveThemeUseCase = mockk()
    private val themeViewModelDelegate: ThemeViewModelDelegate = ThemeViewModelDelegateImpl()

    private lateinit var underTest: MainViewModel
    private lateinit var themeObserver: TestObserver<Theme>

    private fun initViewModel() {
        underTest = MainViewModel(
            observeThemeUseCase,
            provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher),
            themeViewModelDelegate
        )
        themeObserver = underTest.theme.test()
    }

    @Test
    fun testObserveTheme() {
        observeThemeUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ThemeTestData.defaultTheme))
        }

        initViewModel()
        themeViewModelDelegate.setTheme(ThemeTestData.defaultTheme)

        themeObserver.assertValue(ThemeTestData.defaultTheme)
    }
}
