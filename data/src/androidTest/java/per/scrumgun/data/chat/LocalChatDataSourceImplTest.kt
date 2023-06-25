package per.scrumgun.data.chat

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
import per.scrumgun.test.testdata.ChatTestData
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalChatDataSourceImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: RoomDatabaseStorage
    private lateinit var underTest: LocalChatDataSourceImpl

    @Before
    fun setUp() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RoomDatabaseStorage::class.java).build()
        val chatDao = db.chatDao()

        underTest = LocalChatDataSourceImpl(
            chatDao
        )
    }

    @After
    @Throws(IOException::class)
    fun finish() {
        db.close()
    }

    @Test
    fun testObserveChats() = runTest {
        underTest.setChats(ChatTestData.all())

        val result = underTest.observeChats()

        assertThat(
            "result Message should be '[Chat(id=101, userId=101, workMessage=workMessage, blockMessage=blockMessage, sentTime=Wed Nov 23 07:00:00 GMT+07:00 2022), Chat(id=102, userId=102, workMessage=workMessage, blockMessage=blockMessage, sentTime=Wed Nov 23 07:00:00 GMT+07:00 2022)]'",
            result.first(),
            equalTo(ChatTestData.all())
        )
    }
}
