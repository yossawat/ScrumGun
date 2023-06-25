package per.scrumgun.data.theme

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
import per.scrumgun.test.testdata.ThemeTestData
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalThemeDataSourceImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: RoomDatabaseStorage
    private lateinit var underTest: LocalThemeDataSourceImpl

    @Before
    fun setUp() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RoomDatabaseStorage::class.java).build()
        val themeDao = db.themeDao()

        underTest = LocalThemeDataSourceImpl(
            themeDao
        )
    }

    @After
    @Throws(IOException::class)
    fun finish() {
        db.close()
    }

    @Test
    fun testObserveTheme() = runTest {
        underTest.setTheme(ThemeTestData.defaultTheme)

        val result = underTest.observeTheme()

        assertThat(
            "result Theme should be 'Theme(background=#F2F3F5, text=#000000)'",
            result.first(),
            equalTo(ThemeTestData.defaultTheme)
        )
    }
}
