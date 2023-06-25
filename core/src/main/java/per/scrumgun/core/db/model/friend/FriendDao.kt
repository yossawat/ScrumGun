package per.scrumgun.core.db.model.friend

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import per.scrumgun.core.db.model.BaseDao

@Dao
abstract class FriendDao : BaseDao<FriendEntity>() {
    @Query("DELETE FROM friend")
    abstract suspend fun clearAll()

    @Query("SELECT * FROM friend")
    abstract fun observeFindAll(): Flow<List<FriendEntity>>
}
