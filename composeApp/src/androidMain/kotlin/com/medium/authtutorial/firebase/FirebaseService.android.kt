package com.medium.authtutorial.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

actual fun getCurrentFirebaseApp(): FirebaseAppInfo {
    return FirebaseAppInfo(
        projectName = FirebaseApp.getInstance().name,
        projectId = FirebaseApp.getInstance().options.projectId!!
    )
}

actual suspend fun signInWithFirebase(
    email: String,
    password: String
): AppUser {
    val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
    val user = authResult.user
    if (user != null) {
        return user.toAppUser()
    } else {
        throw Exception("User not found")
    }
}

actual suspend fun setFirebaseUserDisplayName(displayName: String) {
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()
        user.updateProfile(profileUpdates).await()
    } else {
        throw Exception("User not found")
    }
}

actual suspend fun createFirebaseUser(
    email: String,
    password: String,
    displayName: String
): AppUser {
    val authResult =
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
    val user = authResult.user
    if (user != null) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()
        user.updateProfile(profileUpdates).await()
        return user.toAppUser()
    } else {
        throw Exception("User creation failed")
    }
}

actual fun getCurrentFirebaseUser(): AppUser? =
    FirebaseAuth.getInstance().currentUser?.toAppUser()

private fun FirebaseUser.toAppUser(): AppUser =
    AppUser(
        userId = uid,
        displayName = displayName ?: "Unknown",
        email = email ?: "Unknown"
    )

actual suspend fun signOutFirebaseUser() {
    // TODO: Listen to auth state changes, we are assuming the user is signed out here
    FirebaseAuth.getInstance().signOut()
}