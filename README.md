# Backdraft 🔥
Kotlin Coroutine Extensions for Firebase 🔥

## Experimental!
This is still a work in progress, and relies on Kotlin's experimental 
`Flow` objects. API's may change as needed. Proceed with caution.

## Firebase Auth

### Auth State

You can observe the current auth state by collecting updates on the 
AuthState Flow:

```kotlin
firebaseAuth.authStateFlow.collect { authState ->
    Log.d("Example", "User changed to: ${authState.currentUser?.uid}")
}
```

### Current User

For convenience, the current user can be observed directly with 
the `userFlow` extension: 

```kotlin

firebaseAuth.currentUser.collect { currentUser ->
    Log.d("Example", "User changed to: ${currentUser?.uid}")
}
```

### ID Token

Firebase's ID Token can be observed with the `idTokenFlow` extension:

```kotlin
firebaseAuth.idTokenFlow.collect { tokenResult ->
    Log.d("Example", "ID Token changed to: ${tokenResult?.token}")
}
```

## Firestore

### Collection Snapshots

Firestore collections can be observed with the `snapshotFlow` extension:

```kotlin
firebaseFirestore
    .collection("users")
    .snapshotFlow
    .collect { snapshot ->
        Log.d("Example", "New data for collection documents. Size: ${snapshot?.documents?.size}")
    }
```

### Document Snapshots:

Firestore documents can be observed with the `snapshotFlow` extension:

```kotlin
firebaseFirestore
    .collection("users")
    .document("id-123")
    .snapshotFlow
    .collect { snapshot ->
        Log.d("Example", "New data for user, ID: ${snapshot?.id}")
    }
```

## Tasks

Any `Task<T>` object can be suspended for a result using `.await()`:

```kotlin
val authResult = firebaseAuth.createUserWithEmailAndPassword("johndoe@example.com", "notsecure").await()
```

