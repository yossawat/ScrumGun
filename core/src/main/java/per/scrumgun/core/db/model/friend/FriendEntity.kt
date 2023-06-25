package per.scrumgun.core.db.model.friend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import per.scrumgun.core.UserId

@Entity(tableName = "friend")
data class FriendEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: UserId,
    @ColumnInfo(name = "name")
    val name: String,
)
