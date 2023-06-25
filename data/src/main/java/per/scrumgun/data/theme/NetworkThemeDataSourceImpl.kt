package per.scrumgun.data.theme

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
import per.scrumgun.core.THEME_CHILD
import per.scrumgun.core.model.theme.NetworkTheme
import per.scrumgun.domain.model.Theme
import per.scrumgun.domain.model.mapper.NetworkThemeToThemeMapper

class NetworkThemeDataSourceImpl(private val databaseReference: DatabaseReference) :
    NetworkThemeDataSource {
    override fun observeTheme(): Flow<Theme> {
        return callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val networkTheme = snapshot.getValue(NetworkTheme::class.java)
                    if (networkTheme != null) {
                        trySendBlocking(NetworkThemeToThemeMapper.map(networkTheme))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    cancel(CancellationException(error.message))
                }
            }

            databaseReference.child(THEME_CHILD).addValueEventListener(listener)

            awaitClose {
                databaseReference.child(THEME_CHILD).removeEventListener(listener)
            }
        }
    }
}
