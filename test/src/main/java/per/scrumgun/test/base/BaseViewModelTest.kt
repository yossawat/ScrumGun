package per.scrumgun.test.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockkStatic
import per.scrumgun.test.rule.CoroutinesTestRule
import org.junit.Before
import org.junit.Rule

abstract class BaseViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        mockkStatic("per.scrumgun.core.interator.UseCaseUtilsKt")
        setUpTest()
    }
    open fun setUpTest() {}
}
