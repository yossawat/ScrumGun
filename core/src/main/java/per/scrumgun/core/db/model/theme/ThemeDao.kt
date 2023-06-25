package per.scrumgun.core.db.model.theme

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import per.scrumgun.core.db.model.BaseDao

@Dao
abstract class ThemeDao : BaseDao<ThemeEntity>() {
    @Query("DELETE FROM theme")
    abstract suspend fun clearAll()

    @Query("SELECT * FROM theme")
    abstract fun observeFindAll(): Flow<ThemeEntity>
}
