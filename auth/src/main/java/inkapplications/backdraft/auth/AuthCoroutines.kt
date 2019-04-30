package inkapplications.backdraft.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import inkapplications.backdraft.tasks.await
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowViaChannel
import kotlinx.coroutines.launch

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

/**
 * Flow that receives changes in Firebase's ID Tokens.
 *
 * @see FirebaseAuth.addIdTokenListener
 */
@FlowPreview
val FirebaseAuth.idTokenFlow: Flow<GetTokenResult?> get() = flowViaChannel { channel ->
    val job = Job()
    val listener = FirebaseAuth.IdTokenListener {
        GlobalScope.launch(job) {
            val token = it.currentUser?.getIdToken(false)?.await()
            channel.send(token)
        }
    }
    addIdTokenListener(listener)

    channel.invokeOnClose {
        removeIdTokenListener(listener)
        job.cancel()
    }
}

