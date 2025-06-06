package com.medium.authtutorial.firebase

interface FirebaseService {
    fun getCurrentApp(): FirebaseAppInfo
    fun getCurrentUser(): AppUser?
    suspend fun signIn(email: String, password: String): AppUser
    suspend fun setDisplayName(displayName: String)
    suspend fun createUser(email: String, password: String, displayName: String): AppUser
    suspend fun signOut()
}

data class FirebaseAppInfo(
    val projectName: String,
    val projectId: String
) {
    override fun toString(): String =
        "$projectName ($projectId)"
}

data class AppUser(
    val userId: String,
    val displayName: String,
    val email: String
) {
    fun greeting(): String =
        "Hello, $displayName! Your email is $email and your user ID is $userId."
}

class FirebaseServiceImpl : FirebaseService {
    override fun getCurrentApp(): FirebaseAppInfo =
        getCurrentFirebaseApp()

    override suspend fun signIn(email: String, password: String): AppUser =
        signInWithFirebase(
            email = email,
            password = password
        )

    override suspend fun setDisplayName(displayName: String) =
        setFirebaseUserDisplayName(
            displayName = displayName
        )

    override suspend fun createUser(email: String, password: String, displayName: String): AppUser =
        createFirebaseUser(
            email = email,
            password = password,
            displayName = displayName
        )

    override fun getCurrentUser(): AppUser? =
        getCurrentFirebaseUser()

    override suspend fun signOut() =
        signOutFirebaseUser()
}

expect fun getCurrentFirebaseApp(): FirebaseAppInfo
expect fun getCurrentFirebaseUser(): AppUser?
expect suspend fun signOutFirebaseUser()
expect suspend fun signInWithFirebase(email: String, password: String): AppUser
expect suspend fun setFirebaseUserDisplayName(displayName: String)
expect suspend fun createFirebaseUser(email: String, password: String, displayName: String): AppUser
