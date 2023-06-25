package per.scrumgun.data.friend

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
import per.scrumgun.core.FRIEND_CHILD
import per.scrumgun.core.NAME_CHILD
import per.scrumgun.domain.model.Friend
import per.scrumgun.domain.model.mapper.NetworkFriendToFriendMapper

class NetworkFriendDataSourceImpl(private val databaseReference: DatabaseReference) :
    NetworkFriendDataSource {
    override fun observeFriends(): Flow<List<Friend>> {
        return callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySendBlocking(getFriends(snapshot))
                }

                override fun onCancelled(error: DatabaseError) {
                    cancel(CancellationException(error.message))
                }
            }

            databaseReference.child(FRIEND_CHILD).addValueEventListener(listener)

            awaitClose {
                databaseReference.child(FRIEND_CHILD).removeEventListener(listener)
            }
        }
    }

    private fun getFriends(result: DataSnapshot): List<Friend> {
        val friendList: MutableList<Friend> = mutableListOf()
        result.children.forEach {
            val id = it.key
            val name = it.child(NAME_CHILD).getValue(String::class.java)
            if (id != null && name != null) {
                friendList.add(
                    NetworkFriendToFriendMapper.map(
                        NetworkFriendToFriendMapper.Params(
                            id = id,
                            name = name
                        )
                    )
                )
            }
        }
        return friendList
    }
}
