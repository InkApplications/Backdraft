package inkapplications.backdraft.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.flowViaChannel

/**
 * Flow that sends document snapshot updates.
 */
val DocumentReference.snapshotFlow get () = flowViaChannel<DocumentSnapshot?> { channel ->
    val registration = addSnapshotListener { snapshot, error ->
        if (error != null) throw error
        channel.offer(snapshot)
    }
    channel.invokeOnClose { registration.remove() }
}
