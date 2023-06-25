package per.scrumgun.domain.model

import per.scrumgun.core.UserId

data class User(
    val id: UserId,
    val name: String,
    val email: String,
)
