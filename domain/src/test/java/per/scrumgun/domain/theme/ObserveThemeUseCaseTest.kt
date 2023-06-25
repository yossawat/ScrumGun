package per.scrumgun.domain.theme

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
import per.scrumgun.test.testdata.ThemeTestData
import per.scrumgun.test.utils.assertValues
import per.scrumgun.test.utils.provideFakeCoroutinesDispatcherProvider

@RunWith(JUnit4::class)
class ObserveThemeUseCaseTest : BaseUseCaseTest() {
    private val themeRepository: ThemeRepository = mockk()
    private lateinit var underTest: ObserveThemeUseCase

    override fun setUpTest() {
        underTest = ObserveThemeUseCase(
            themeRepository,
        )
    }

    @Test
    fun testObserveTheme() = runTest {
        coEvery {
            themeRepository.observeTheme()
        } returns flowOf(ThemeTestData.defaultTheme)

        val result = underTest.invoke(
            Unit,
            provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
        ).toList()

        result.assertValues(
            Result.Progress,
            Result.Success(ThemeTestData.defaultTheme)
        )
        verify(exactly = 1) {
            themeRepository.observeTheme()
        }
    }
}
