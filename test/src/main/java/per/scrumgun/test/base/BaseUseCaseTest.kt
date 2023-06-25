package per.scrumgun.test.base

import per.scrumgun.test.rule.CoroutinesTestRule
import org.junit.Before
import org.junit.Rule

abstract class BaseUseCaseTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        setUpTest()
    }

    protected abstract fun setUpTest()
}
