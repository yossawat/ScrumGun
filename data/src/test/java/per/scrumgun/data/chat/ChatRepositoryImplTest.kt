package per.scrumgun.data.chat

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
import per.scrumgun.test.testdata.ChatTestData

@RunWith(JUnit4::class)
class ChatRepositoryImplTest {
    private val networkChatDataSource: NetworkChatDataSource = mockk()
    private val localChatDataSource: LocalChatDataSource = mockk()

    private lateinit var underTest: ChatRepositoryImpl

    @Before
    fun setUp() {
        underTest = ChatRepositoryImpl(
            networkChatDataSource,
            localChatDataSource,
        )
    }

    @Test
    fun testObserveFriends() = runTest {
        coEvery {
            networkChatDataSource.observeChat()
        } returns flowOf(ChatTestData.all())
        coEvery {
            localChatDataSource.setChats(any())
        } returns Unit
        coEvery {
            localChatDataSource.observeChats()
        } returns flowOf(ChatTestData.all())

        val result = underTest.observeChat()

        assertThat(
            "result Chat first should be '[Chat(id=101, userId=101, workMessage=workMessage, blockMessage=blockMessage, sentTime=Wed Nov 23 07:00:00 GMT+07:00 2022), Chat(id=102, userId=102, workMessage=workMessage, blockMessage=blockMessage, sentTime=Wed Nov 23 07:00:00 GMT+07:00 2022)]'",
            result.first(),
            equalTo(ChatTestData.all())
        )
        coVerify(exactly = 1) {
            networkChatDataSource.observeChat()
            localChatDataSource.setChats(ChatTestData.all())
            localChatDataSource.observeChats()
        }
    }
}
