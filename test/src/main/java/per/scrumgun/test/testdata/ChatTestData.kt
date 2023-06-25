package per.scrumgun.test.testdata

import per.scrumgun.domain.model.Chat
import per.scrumgun.test.testdata.TimeTestData.EPOCH_2022_11_23_MIDNIGHT
import java.util.*

object ChatTestData {
    private val chat1 = Chat(
        id = "101",
        userId = "101",
        workMessage = "workMessage",
        blockMessage = "blockMessage",
        sentTime = Date(EPOCH_2022_11_23_MIDNIGHT)
    )

    private val chat2 = Chat(
        id = "102",
        userId = "102",
        workMessage = "workMessage",
        blockMessage = "blockMessage",
        sentTime = Date(EPOCH_2022_11_23_MIDNIGHT)
    )

    fun all() = listOf(
        chat1,
        chat2,
    )
}
