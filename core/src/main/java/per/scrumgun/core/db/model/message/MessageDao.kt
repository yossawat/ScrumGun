package per.scrumgun.core.db.model.message

import androidx.room.Dao
import androidx.room.Query
import per.scrumgun.core.db.model.BaseDao

@Dao
abstract class MessageDao : BaseDao<MessageEntity>() {
    @Query("DELETE FROM message")
    abstract suspend fun clearAll()

    @Query("SELECT * FROM message")
    abstract suspend fun findAll(): MessageEntity?
}
