package inkapplications.backdraft.firestore

import com.google.firebase.firestore.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowViaChannel

/**
 * Send snapshot updates from a collection into a flow.
 */
@FlowPreview
val CollectionReference.snapshotFlow get() = flowViaChannel<QuerySnapshot?> { channel ->
    val registration = addSnapshotListener { snapshot, error ->
        if (error != null) throw error
        channel.offer(snapshot)
    }

    channel.invokeOnClose { registration.remove() }
}
