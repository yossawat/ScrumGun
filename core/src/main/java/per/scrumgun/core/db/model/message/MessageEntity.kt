package per.scrumgun.core.db.model.message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import per.scrumgun.core.MessageId

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: MessageId,
    @ColumnInfo(name = "workMessage")
    val workMessage: String?,
    @ColumnInfo(name = "blockMessage")
    val blockMessage: String?,
)
