package per.scrumgun.data.friend

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import per.scrumgun.core.db.RoomDatabaseStorage
import per.scrumgun.test.testdata.FriendTestData
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalFriendDataSourceImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: RoomDatabaseStorage
    private lateinit var underTest: LocalFriendDataSourceImpl

    @Before
    fun setUp() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RoomDatabaseStorage::class.java).build()
        val friendDao = db.friendDao()

        underTest = LocalFriendDataSourceImpl(
            friendDao
        )
    }

    @After
    @Throws(IOException::class)
    fun finish() {
        db.close()
    }

    @Test
    fun testObserveFriends() = runTest {
        underTest.setFriends(FriendTestData.all())

        val result = underTest.observeFriends()

        assertThat(
            "result Message should be '[Friend(id=101, name=friend1), Friend(id=102, name=friend2)]'",
            result.first(),
            equalTo(FriendTestData.all())
        )
    }
}
