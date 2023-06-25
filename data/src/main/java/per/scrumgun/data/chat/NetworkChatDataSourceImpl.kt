package per.scrumgun.data.chat

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import per.scrumgun.core.CHAT_CHILD
import per.scrumgun.core.model.chat.NetworkChat
import per.scrumgun.domain.model.Chat
import per.scrumgun.domain.model.mapper.NetworkChatsToChatsMapper

class NetworkChatDataSourceImpl(private val databaseReference: DatabaseReference) :
    NetworkChatDataSource {
    override fun observeChat(): Flow<List<Chat>> {
        return callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySendBlocking(getChats(snapshot))
                }

                override fun onCancelled(error: DatabaseError) {
                    cancel(CancellationException(error.message))
                }
            }

            databaseReference.child(CHAT_CHILD).addValueEventListener(listener)

            awaitClose {
                databaseReference.child(CHAT_CHILD).removeEventListener(listener)
            }
        }
    }

    private fun getChats(result: DataSnapshot): List<Chat> {
        val chatsList: MutableList<Chat> = mutableListOf()
        result.children.forEach {
            val id = it.key
            val chats = it.getValue(NetworkChat::class.java)
            if (id != null && chats != null) {
                chatsList.add(
                    NetworkChatsToChatsMapper.map(
                        NetworkChatsToChatsMapper.Params(
                            id = id,
                            chat = chats
                        )
                    )
                )
            }
        }
        return chatsList
    }
}
