package per.scrumgun.core.db.model.user

import androidx.room.Dao
import androidx.room.Query
import per.scrumgun.core.db.model.BaseDao

@Dao
abstract class UserDao : BaseDao<UserEntity>() {
    @Query("DELETE FROM user")
    abstract suspend fun clearAll()

    @Query("SELECT * FROM user")
    abstract suspend fun findAll(): UserEntity?
}
