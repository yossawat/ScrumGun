package per.scrumgun.test.testdata

import per.scrumgun.domain.model.Friend

object FriendTestData {
    private val friend1 = Friend(
        id = "101",
        name = "friend1"
    )
    private val friend2 = Friend(
        id = "102",
        name = "friend2"
    )

    fun all() = listOf(
        friend1,
        friend2,
    )
}
