package inkapplications.backdraft.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Flow that sends document snapshot updates.
 */
val DocumentReference.snapshotFlow get () = callbackFlow<DocumentSnapshot?> {
    val registration = addSnapshotListener { snapshot, error ->
        if (error != null) cancel(CancellationException("Snapshot returned Error", error))
        else offer(snapshot)
    }
    awaitClose { registration.remove() }
}
