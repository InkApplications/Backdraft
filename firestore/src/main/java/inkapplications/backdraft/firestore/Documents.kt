package inkapplications.backdraft.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.flowViaChannel

/**
 * Flow that sends document snapshot updates.
 */
val DocumentReference.snapshotFlow get () = flowViaChannel<DocumentSnapshot?> { channel ->
    val registration = addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
        if (firebaseFirestoreException != null) throw firebaseFirestoreException
        channel.offer(documentSnapshot)
    }
    channel.invokeOnClose { registration.remove() }
}
