package per.scrumgun.core.model.theme

import com.google.firebase.database.PropertyName

data class NetworkTheme(
    @field:PropertyName("background")
    var background: String? = null,
    @field:PropertyName("text")
    var text: String? = null,
)
