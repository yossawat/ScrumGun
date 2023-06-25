package per.scrumgun.core.db.model.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import per.scrumgun.core.ChatId
import per.scrumgun.core.UserId
import java.util.*

@Entity(tableName = "chat")
data class ChatEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: ChatId,
    @ColumnInfo(name = "userId")
    val userId: UserId,
    @ColumnInfo(name = "workMessage")
    val workMessage: String,
    @ColumnInfo(name = "blockMessage")
    val blockMessage: String,
    @ColumnInfo(name = "sentTime")
    val sentTime: Date,
)
