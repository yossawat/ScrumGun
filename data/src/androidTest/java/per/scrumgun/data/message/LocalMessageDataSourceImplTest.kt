package per.scrumgun.data.message

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
import per.scrumgun.test.testdata.MessageTestData
import per.scrumgun.test.testdata.ThemeTestData
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalMessageDataSourceImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: RoomDatabaseStorage
    private lateinit var underTest: LocalMessageDataSourceImpl

    @Before
    fun setUp() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RoomDatabaseStorage::class.java).build()
        val messageDao = db.messageDao()

        underTest = LocalMessageDataSourceImpl(
            messageDao
        )
    }

    @After
    @Throws(IOException::class)
    fun finish() {
        db.close()
    }

    @Test
    fun testGetMessage() = runTest {
        underTest.setWorkMessage("workMessage")
        underTest.setBlockMessage("blockMessage")

        val result = underTest.getMessage()

        assertThat(
            "result Message should be 'Message(id=101, workMessage=workMessage, blockMessage=blockMessage)'",
            result,
            equalTo(MessageTestData.messageNotEmpty)
        )
    }
}
