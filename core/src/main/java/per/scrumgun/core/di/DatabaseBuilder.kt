package per.scrumgun.core.di

import android.content.Context
import androidx.room.Room
import per.scrumgun.core.db.RoomDatabaseStorage

class DatabaseBuilder(private val context: Context) {
    fun buildRoomDatabaseStorage(): RoomDatabaseStorage {
        return Room.databaseBuilder(
            context,
            RoomDatabaseStorage::class.java,
            "room-database-storage"
        ).fallbackToDestructiveMigration().build()
    }
}
