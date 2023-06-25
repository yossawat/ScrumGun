package per.scrumgun.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import per.scrumgun.core.db.model.chat.ChatDao
import per.scrumgun.core.db.model.chat.ChatEntity
import per.scrumgun.core.db.model.friend.FriendDao
import per.scrumgun.core.db.model.friend.FriendEntity
import per.scrumgun.core.db.model.message.MessageDao
import per.scrumgun.core.db.model.message.MessageEntity
import per.scrumgun.core.db.model.theme.ThemeDao
import per.scrumgun.core.db.model.theme.ThemeEntity
import per.scrumgun.core.db.model.user.UserDao
import per.scrumgun.core.db.model.user.UserEntity

@Database(
    entities = [
        UserEntity::class,
        MessageEntity::class,
        FriendEntity::class,
        ChatEntity::class,
        ThemeEntity::class,
    ],
    version = 8
)
@TypeConverters(DateConverter::class, LongListConverter::class)
abstract class RoomDatabaseStorage : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun friendDao(): FriendDao
    abstract fun chatDao(): ChatDao
    abstract fun themeDao(): ThemeDao
}
