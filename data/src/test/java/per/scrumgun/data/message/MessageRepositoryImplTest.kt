package per.scrumgun.data.message

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MessageRepositoryImplTest {
    private val localMessageDataSource: LocalMessageDataSource = mockk()
    private val networkMessageDataSource: NetworkMessageDataSource = mockk()

    private lateinit var underTest: MessageRepositoryImpl

    @Before
    fun setUp() {
        underTest = MessageRepositoryImpl(
            localMessageDataSource,
            networkMessageDataSource,
        )
    }

    @Test
    fun testSetWorkMessage() = runTest {
        coEvery {
            localMessageDataSource.setWorkMessage(any())
        } returns Unit

        underTest.setWorkMessage("workMessage")

        coVerify(exactly = 1) {
            localMessageDataSource.setWorkMessage("workMessage")
        }
    }

    @Test
    fun testSetBlockMessage() = runTest {
        coEvery {
            localMessageDataSource.setBlockMessage(any())
        } returns Unit

        underTest.setBlockMessage("blockMessage")

        coVerify(exactly = 1) {
            localMessageDataSource.setBlockMessage("blockMessage")
        }
    }
}
