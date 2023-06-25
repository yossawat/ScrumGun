package per.scrumgun.domain.model

import per.scrumgun.core.UserId

data class Friend(
    val id: UserId,
    val name: String,
)
