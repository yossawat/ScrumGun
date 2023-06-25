package per.scrumgun.core.di

import per.scrumgun.core.db.RoomDatabaseStorage
import per.scrumgun.core.db.model.chat.ChatDao
import per.scrumgun.core.db.model.friend.FriendDao
import per.scrumgun.core.db.model.message.MessageDao
import per.scrumgun.core.db.model.theme.ThemeDao
import per.scrumgun.core.db.model.user.UserDao

class DaoBuilder(
    private val roomDatabaseStorage: RoomDatabaseStorage,
) {
    fun getUserDao(): UserDao = roomDatabaseStorage.userDao()
    fun getMessageDao(): MessageDao = roomDatabaseStorage.messageDao()
    fun getFriendDao(): FriendDao = roomDatabaseStorage.friendDao()
    fun getChatDao(): ChatDao = roomDatabaseStorage.chatDao()
    fun getThemeDao(): ThemeDao = roomDatabaseStorage.themeDao()
}
