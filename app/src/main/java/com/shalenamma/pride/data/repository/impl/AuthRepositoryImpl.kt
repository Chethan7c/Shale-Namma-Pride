package com.shalenamma.pride.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shalenamma.pride.data.models.User
import com.shalenamma.pride.data.models.UserRole
import com.shalenamma.pride.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun getCurrentUser(): Flow<User?> {
        return auth.currentUser?.let { firebaseUser ->
            flow {
                try {
                    val email = firebaseUser.email ?: ""
                    val roleResult = isEmailAllowed(email)
                    val role = roleResult.getOrElse { UserRole.PARENT }

                    val user = User(
                        id = firebaseUser.uid,
                        email = email,
                        name = firebaseUser.displayName ?: "",
                        profileImageUrl = firebaseUser.photoUrl?.toString() ?: "",
                        role = role
                    )
                    emit(user)
                } catch (e: Exception) {
                    emit(null)
                }
            }
        } ?: flowOf(null)
    }

    override suspend fun isEmailAllowed(email: String): Result<UserRole> {
        return try {
            val doc = firestore.collection("allowed_users").document(email).get().await()
            if (doc.exists()) {
                val roleStr = doc.getString("role") ?: "TEACHER"
                Result.success(UserRole.fromSafeString(roleStr))
            } else {
                Result.failure(Exception("Unauthorized: this email is not allowed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithEmail(email: String, password: String): Result<User> {
        return try {
            val roleResult = isEmailAllowed(email)
            val role = roleResult.getOrElse { return Result.failure(it) }

            val result = auth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("User ID not found")

            // Fetch user data from Firestore safely
            val userDoc = firestore.collection("users").document(uid).get().await()
            val user = if (userDoc.exists()) {
                User.fromMap(userDoc.data ?: emptyMap()).copy(role = role)
            } else {
                User(id = uid, email = email, role = role)
            }

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        name: String
    ): Result<User> {
        return try {
            // Create Firebase Auth user
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("User ID not found")

            // Create user document in Firestore
            val user = User(
                id = uid,
                email = email,
                name = name,
                role = UserRole.STUDENT,
                createdAt = Date(),
                updatedAt = Date()
            )

            firestore.collection("users").document(uid).set(user).await()

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun updateUserProfile(name: String, profileImageUrl: String): Result<Unit> {
        return try {
            val uid = auth.currentUser?.uid ?: throw Exception("No user logged in")

            firestore.collection("users").document(uid).update(
                mapOf(
                    "name" to name,
                    "profileImageUrl" to profileImageUrl,
                    "updatedAt" to Date()
                )
            ).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
