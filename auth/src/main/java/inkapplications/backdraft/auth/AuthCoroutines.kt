package inkapplications.backdraft.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import inkapplications.backdraft.tasks.await
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Flow that receives changes in Firebase's Authentication state.
 *
 * @see FirebaseAuth.addAuthStateListener
 */
@FlowPreview
val FirebaseAuth.authStateFlow: Flow<FirebaseAuth> get() = callbackFlow {
    val listener: (FirebaseAuth) -> Unit = { offer(it) }
    addAuthStateListener(listener)
    awaitClose { removeAuthStateListener(listener) }
}

/**
 * Flow that receives changes in Firebase's ID Tokens.
 *
 * @see FirebaseAuth.addIdTokenListener
 */
@FlowPreview
val FirebaseAuth.idTokenFlow: Flow<GetTokenResult?> get() = callbackFlow {
    val job = Job()
    val listener = FirebaseAuth.IdTokenListener {
        GlobalScope.launch(job) {
            val token = it.currentUser?.getIdToken(false)?.await()
            send(token)
        }
    }
    addIdTokenListener(listener)

    awaitClose {
        removeIdTokenListener(listener)
        job.cancel()
    }
}

/**
 * Flow that receives changes in the User/logged in state.
 *
 * @see FirebaseAuth.addAuthStateListener
 */
@FlowPreview
val FirebaseAuth.userFlow: Flow<FirebaseUser?> get() = flow {
    authStateFlow.collect { emit(it.currentUser) }
}
