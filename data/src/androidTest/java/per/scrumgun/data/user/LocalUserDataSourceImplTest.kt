package per.scrumgun.data.user

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import per.scrumgun.core.db.RoomDatabaseStorage
import per.scrumgun.test.testdata.UserTestData
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalUserDataSourceImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: RoomDatabaseStorage
    private lateinit var underTest: LocalUserDataSourceImpl

    @Before
    fun setUp() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RoomDatabaseStorage::class.java).build()
        val userDao = db.userDao()

        underTest = LocalUserDataSourceImpl(
            userDao
        )
    }

    @After
    @Throws(IOException::class)
    fun finish() {
        db.close()
    }

    @Test
    fun testGetUser() = runTest {
        underTest.setUser(UserTestData.user)

        val result = underTest.getUser()

        assertThat(
            "result user should be 'User(id = 101, name = name, email = email)'",
            result,
            equalTo(UserTestData.user)
        )
    }
}
