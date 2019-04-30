package inkapplications.backdraft.tasks

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Suspend the coroutine until a task returns a result.
 *
 * @receiver The task to wait for results of.
 * @return The result of the task, when successful.
 * @throws Exception on task failure.
 */
suspend fun <T> Task<T>.await(): T = suspendCancellableCoroutine { continuation ->
    addOnSuccessListener { continuation.resume(it) }
    addOnFailureListener { continuation.resumeWithException(it) }
}
