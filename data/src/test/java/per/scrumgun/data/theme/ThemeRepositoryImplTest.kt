package per.scrumgun.data.theme

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.test.testdata.ThemeTestData

@RunWith(JUnit4::class)
class ThemeRepositoryImplTest {
    private val networkThemeDataSource: NetworkThemeDataSource = mockk()
    private val localThemeDataSource: LocalThemeDataSource = mockk()

    private lateinit var underTest: ThemeRepositoryImpl

    @Before
    fun setUp() {
        underTest = ThemeRepositoryImpl(
            networkThemeDataSource,
            localThemeDataSource,
        )
    }

    @Test
    fun testObserveTheme() = runTest {
        coEvery {
            networkThemeDataSource.observeTheme()
        } returns flowOf(ThemeTestData.defaultTheme)
        coEvery {
            localThemeDataSource.setTheme(any())
        } returns Unit
        coEvery {
            localThemeDataSource.observeTheme()
        } returns flowOf(ThemeTestData.defaultTheme)

        val result = underTest.observeTheme()

        assertThat(
            "result Theme should be 'Theme(background=#F2F3F5, text=#000000)'",
            result.first(),
            equalTo(ThemeTestData.defaultTheme)
        )
        coVerify(exactly = 1) {
            networkThemeDataSource.observeTheme()
            localThemeDataSource.setTheme(ThemeTestData.defaultTheme)
            localThemeDataSource.observeTheme()
        }
    }
}
