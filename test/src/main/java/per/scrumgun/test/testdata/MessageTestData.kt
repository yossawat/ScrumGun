package per.scrumgun.test.testdata

import per.scrumgun.core.MESSAGE_ID
import per.scrumgun.core.db.model.message.MessageEntity
import per.scrumgun.domain.model.Message

object MessageTestData {
    val messageNotEmpty = Message(
        id = 101,
        workMessage = "workMessage",
        blockMessage = "blockMessage",
    )
    val messageWorkEmpty = Message(
        id = 101,
        workMessage = null,
        blockMessage = "blockMessage",
    )
    val messageBlockEmpty = Message(
        id = 101,
        workMessage = "workMessage",
        blockMessage = null,
    )
    val messageEmpty = Message(
        id = 101,
        workMessage = null,
        blockMessage = null,
    )

    object MessageEntityTestData {
        val messageEntityNotEmpty = MessageEntity(
            id = MESSAGE_ID,
            workMessage = "workMessage",
            blockMessage = "blockMessage"
        )
        val messageEntityEmpty = MessageEntity(
            id = MESSAGE_ID,
            workMessage = null,
            blockMessage = null
        )
    }
}
