package inkapplications.backdraft.firestore

import com.google.firebase.firestore.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Send snapshot updates from a collection into a flow.
 */
@FlowPreview
val Query.snapshotFlow get() = callbackFlow<QuerySnapshot?> {
    val registration = addSnapshotListener { snapshot, error ->
        if (error != null) cancel(CancellationException("Snapshot returned Error", error))
        else offer(snapshot)
    }

    awaitClose { registration.remove() }
}
