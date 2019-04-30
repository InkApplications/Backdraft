package inkapplications.backdraft.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowViaChannel

/**
 * Flow that receives changes in Firebase's Authentication state.
 *
 * @see FirebaseAuth.addAuthStateListener
 */
@FlowPreview
val FirebaseAuth.authStateFlow: Flow<FirebaseAuth> get() = flowViaChannel { channel ->
    val listener: (FirebaseAuth) -> Unit = { channel.offer(it) }
    addAuthStateListener(listener)
    channel.invokeOnClose { removeAuthStateListener(listener) }
}
