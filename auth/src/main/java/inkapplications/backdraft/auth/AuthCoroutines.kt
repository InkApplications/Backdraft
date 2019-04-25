package inkapplications.backdraft.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Flow that receives changes in Firebase's Authentication state.
 *
 * @see FirebaseAuth.addAuthStateListener
 */
@FlowPreview
val FirebaseAuth.authStateFlow: Flow<FirebaseAuth> get() = flow {
    val channel = Channel<FirebaseAuth>(Channel.UNLIMITED)
    val listener: (FirebaseAuth) -> Unit = { channel.offer(it) }
    addAuthStateListener(listener)

    try {
        channel.consumeEach { emit(it) }
    } finally {
        removeAuthStateListener(listener)
    }
}
