package per.scrumgun.core.db.model.theme

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import per.scrumgun.core.ThemeId

@Entity(tableName = "theme")
data class ThemeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: ThemeId,
    @ColumnInfo(name = "background")
    val background: String,
    @ColumnInfo(name = "text")
    val text: String,
)
