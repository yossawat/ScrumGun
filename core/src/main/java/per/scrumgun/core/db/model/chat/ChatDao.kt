package per.scrumgun.core.db.model.chat

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import per.scrumgun.core.db.model.BaseDao

@Dao
abstract class ChatDao : BaseDao<ChatEntity>() {
    @Query("DELETE FROM chat")
    abstract suspend fun clearAll()

    @Query("SELECT * FROM chat ORDER BY sentTime ASC")
    abstract fun observeFindAll(): Flow<List<ChatEntity>>
}
