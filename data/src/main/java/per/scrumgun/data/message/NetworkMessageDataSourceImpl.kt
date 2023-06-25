package per.scrumgun.data.message

import com.google.firebase.database.DatabaseReference
import per.scrumgun.core.CHAT_CHILD
import per.scrumgun.core.FRIEND_CHILD
import per.scrumgun.core.NAME_CHILD
import per.scrumgun.core.UserId
import per.scrumgun.core.util.DateUtils
import per.scrumgun.data.model.form.SendMessageNetworkForm
import per.scrumgun.domain.model.form.SendMessageForm

class NetworkMessageDataSourceImpl(private val databaseReference: DatabaseReference) :
    NetworkMessageDataSource {
    override suspend fun sendFriend(userId: UserId, userName: String) {
        databaseReference.child(FRIEND_CHILD).child(userId).child(NAME_CHILD)
            .setValue(userName)
    }

    override suspend fun sendMessage(sendMessageForm: SendMessageForm) {
        databaseReference.child(CHAT_CHILD)
            .child(DateUtils.formatYearMonthDateTimeWithRandomString())
            .setValue(
                SendMessageNetworkForm(
                    userId = sendMessageForm.userId,
                    workMessage = sendMessageForm.workMessage,
                    blockMessage = sendMessageForm.blockMessage,
                    sentTime = sendMessageForm.sentTime
                )
            )
    }
}
