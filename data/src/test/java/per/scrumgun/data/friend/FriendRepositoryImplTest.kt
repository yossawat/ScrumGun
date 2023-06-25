package per.scrumgun.data.friend

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
import per.scrumgun.test.testdata.FriendTestData

@RunWith(JUnit4::class)
class FriendRepositoryImplTest {
    private val networkFriendDataSource: NetworkFriendDataSource = mockk()
    private val localFriendDataSource: LocalFriendDataSource = mockk()

    private lateinit var underTest: FriendRepositoryImpl

    @Before
    fun setUp() {
        underTest = FriendRepositoryImpl(
            networkFriendDataSource,
            localFriendDataSource,
        )
    }

    @Test
    fun testObserveFriends() = runTest {
        coEvery {
            networkFriendDataSource.observeFriends()
        } returns flowOf(FriendTestData.all())
        coEvery {
            localFriendDataSource.setFriends(any())
        } returns Unit
        coEvery {
            localFriendDataSource.observeFriends()
        } returns flowOf(FriendTestData.all())

        val result = underTest.observeFriends()

        assertThat(
            "result Friend should be '[Friend(id=101, name=friend1), Friend(id=102, name=friend2)]'",
            result.first(),
            equalTo(FriendTestData.all())
        )
        coVerify(exactly = 1) {
            networkFriendDataSource.observeFriends()
            localFriendDataSource.setFriends(FriendTestData.all())
            localFriendDataSource.observeFriends()
        }
    }
}
