package per.scrumgun.core.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyFirebaseDatabaseBuilder {
    fun build(): DatabaseReference {
        return Firebase.database.reference
    }
}
