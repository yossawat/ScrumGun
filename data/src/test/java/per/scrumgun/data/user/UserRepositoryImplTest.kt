package per.scrumgun.data.user

import android.content.Intent
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.domain.chat.ChatRepository
import per.scrumgun.domain.friend.FriendRepository
import per.scrumgun.domain.message.MessageRepository
import per.scrumgun.test.testdata.UserTestData

@RunWith(JUnit4::class)
class UserRepositoryImplTest {
    private val networkUserDataSource: NetworkUserDataSource = mockk()
    private val localUserDataSource: LocalUserDataSource = mockk()
    private val messageRepository: MessageRepository = mockk()
    private val friendRepository: FriendRepository = mockk()
    private val chatRepository: ChatRepository = mockk()

    private lateinit var underTest: UserRepositoryImpl

    @Before
    fun setUp() {
        underTest = UserRepositoryImpl(
            networkUserDataSource,
            localUserDataSource,
            messageRepository,
            friendRepository,
            chatRepository
        )
    }

    @Test
    fun testSetUser() = runTest {
        val data: Intent = mockk()
        coEvery {
            networkUserDataSource.setUser(data)
        } returns UserTestData.user
        coEvery {
            localUserDataSource.setUser(UserTestData.user)
        } returns Unit

        underTest.setUser(data)

        coVerifySequence {
            networkUserDataSource.setUser(data)
            localUserDataSource.setUser(UserTestData.user)
        }
    }

    @Test
    fun testGetUser() = runTest {
        coEvery {
            localUserDataSource.getUser()
        } returns UserTestData.user

        val result = underTest.getUser()

        assertThat(
            "result User should be 'User(id = 101, name = name, email = email)'",
            result,
            equalTo(UserTestData.user)
        )
        coVerify(exactly = 1) {
            underTest.getUser()
        }
    }

    @Test
    fun testLogout() = runTest {
        coEvery {
            localUserDataSource.logout()
        } returns Unit
        coEvery {
            messageRepository.clearAll()
        } returns Unit
        coEvery {
            friendRepository.clearAll()
        } returns Unit
        coEvery {
            chatRepository.clearAll()
        } returns Unit

        underTest.logout()

        coVerifyOrder {
            localUserDataSource.logout()
            messageRepository.clearAll()
            friendRepository.clearAll()
            chatRepository.clearAll()
        }
    }
}
