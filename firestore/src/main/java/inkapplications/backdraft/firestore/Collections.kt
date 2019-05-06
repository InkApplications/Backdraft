package inkapplications.backdraft.firestore

import com.google.firebase.firestore.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowViaChannel

/**
 * Send snapshot updates from a collection into a flow.
 */
@FlowPreview
val CollectionReference.snapshotFlow get() = flowViaChannel<QuerySnapshot?> { channel ->
    val listener = EventListener<QuerySnapshot?> { snapshot, error ->
        if (error != null) throw error
        channel.offer(snapshot)
    }
    val registration = addSnapshotListener(listener)

    channel.invokeOnClose { registration.remove() }
}
