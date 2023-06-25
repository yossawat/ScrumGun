package per.scrumgun.core.model.chat

import com.google.firebase.database.PropertyName
import per.scrumgun.core.UserId

data class NetworkChat(
    @field:PropertyName("userId")
    var userId: UserId? = null,
    @field:PropertyName("workMessage")
    var workMessage: String? = null,
    @field:PropertyName("blockMessage")
    var blockMessage: String? = null,
    @field:PropertyName("sentTime")
    var sentTime: String? = null,
)
